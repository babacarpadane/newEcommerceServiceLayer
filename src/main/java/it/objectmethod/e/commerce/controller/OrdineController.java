package it.objectmethod.e.commerce.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.OrdineRepository;
import it.objectmethod.e.commerce.controller.service.JWTService;
import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Ordine;
import it.objectmethod.e.commerce.entity.RigaOrdine;

@RestController
@RequestMapping("/api/ordine")
public class OrdineController {
	@Autowired
	private CartRepository carRep;
	@Autowired
	private OrdineRepository ordRep;
	@Autowired
	private JWTService jwtSer;

	@PostMapping("/genera-ordine")
	public ResponseEntity<Ordine> stampaOrdine(@RequestHeader("authentificationToken") String token) {
		ResponseEntity<Ordine> resp = null;
		String nomeUtente = jwtSer.getUsername(token); //si prende il token da solo
		Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);
		if (carrello != null && !carrello.getListaSpesa().isEmpty()) {
			Ordine ordine = new Ordine();

			String codeForNewOrder = null;
			try {
				String code = ordRep.findLastNumeroOrdine();
				String ch = code.substring(0, 1);
				int num = Integer.parseInt(code.substring(1)) + 1;
				String formattedNum = String.format("%06d", num);
				codeForNewOrder = ch.concat(formattedNum);
			} catch (NullPointerException e) {
				e.printStackTrace();
				codeForNewOrder = "A000000";
			}
			ordine.setNumeroOrdine(codeForNewOrder);
			ordine.setProprietarioOrdine(carrello.getProprietarioCarrello());
			Date data = Date.valueOf(LocalDate.now());
			ordine.setDataOrdine(data);

			List<RigaOrdine> listaRighe = new ArrayList<RigaOrdine>();
			for (CartDetail detail : carrello.getListaSpesa()) {
				RigaOrdine riga = new RigaOrdine();
				riga.setArticolo(detail.getArticolo());
				riga.setQuantita(detail.getQuantita());
				listaRighe.add(riga);
			}
			ordine.setRigheOrdine(listaRighe);
			ordine = ordRep.save(ordine);
			carrello.getListaSpesa().removeAll(carrello.getListaSpesa());
			carrello = carRep.save(carrello);
			resp = new ResponseEntity<Ordine>(ordine, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<Ordine>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
package it.objectmethod.e.commerce.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
	public ResponseEntity<Ordine> stampaOrdine(HttpServletRequest req) {
		String token = req.getHeader("headerName");
		String nomeUtente = jwtSer.getUsername(token);
		ResponseEntity<Ordine> resp = null;
		Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);
		if (carrello == null) {
			resp = new ResponseEntity<Ordine>(HttpStatus.BAD_REQUEST);
		} else {
			Ordine ordine = new Ordine();

			String code = ordRep.findLastNumeroOrdine();
			String ch = code.substring(0, 1);
			int num = Integer.parseInt(code.substring(1)) + 1;
			String formattedNum = String.format("%06d", num);
			
			ordine.setNumeroOrdine(ch.concat(formattedNum));
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

			resp = new ResponseEntity<Ordine>(ordine, HttpStatus.OK);
		}
		return resp;
	}
}
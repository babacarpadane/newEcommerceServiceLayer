package it.objectmethod.e.commerce.controller;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.OrdineRepository;
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

	List<String> codeList = new ArrayList<String>();

	@PostMapping("/genera-ordine")
	public ResponseEntity<Ordine> stampaOrdine(@RequestParam("id_utente") Long idUtente) {
		ResponseEntity<Ordine> resp = null;
		Cart carrello = carRep.findByProprietarioCarrelloIdUtente(idUtente);
		if (carrello == null) {
			resp = new ResponseEntity<Ordine>(HttpStatus.BAD_REQUEST);
		} else {
			Ordine ordine = new Ordine();

			String code = null;
			int i = 0;
			char c = 'A';
			do {
				StringBuilder codice = new StringBuilder();
				String formattedNum = String.format("%06d", i);
				codice.append((char) c);
				codice.append((String) (formattedNum));
				code = codice.toString();
				if (i == 999999) {
					i = 0;
					c++;
				} else {
					i++;
				}
			} while (codeList.contains(code));
			codeList.add(code);

			ordine.setNumeroOrdine(code);
			ordine.setProprietarioOrdine(carrello.getProprietarioCarrello());
			Date data = Date.valueOf(LocalDate.now());
			ordine.setDataOrdine(data);

			List<RigaOrdine> listaRighe = new ArrayList<RigaOrdine>();
			for (CartDetail detail : carrello.getListaSpesa()) {
				RigaOrdine riga = new RigaOrdine();
				riga.setArticolo(detail.getArticolo());
				riga.setQuantita(detail.getQuantita());
				riga.setOrdine(ordine);
				listaRighe.add(riga);
			}
			ordine.setRigheOrdine(listaRighe);
			ordine = ordRep.save(ordine);

			resp = new ResponseEntity<Ordine>(ordine, HttpStatus.OK);
		}
		return resp;
	}
}
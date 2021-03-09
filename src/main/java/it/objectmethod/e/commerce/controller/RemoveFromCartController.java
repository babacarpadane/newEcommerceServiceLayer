package it.objectmethod.e.commerce.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.repository.CartRepository;

@RestController
@RequestMapping("/api/cart")
public class RemoveFromCartController {
	@Autowired
	private ArticoloRepository artRep;
	@Autowired
	private CartRepository carRep;

	@GetMapping("/remove")
	public ResponseEntity<Cart> rimuoviProdotto(@RequestParam("id_art") Integer idArticolo, HttpServletRequest req) {
		ResponseEntity<Cart> resp = null;
		Articolo art = artRep.findById(idArticolo).get();
		String nomeUtente = req.getAttribute("nomeUtente").toString();
		Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);
		
		if (carrello != null  && !carrello.getListaSpesa().isEmpty() && art != null) {
			for (CartDetail detail : carrello.getListaSpesa()) {
				if (detail.getArticolo().getIdArticolo().equals(art.getIdArticolo())) {
					art.setDisponibilita(art.getDisponibilita() + detail.getQuantita());
					carrello.getListaSpesa().remove(detail);
					carrello = carRep.save(carrello);
					resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
					break;
				}
			}
		} else {
			resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}

}

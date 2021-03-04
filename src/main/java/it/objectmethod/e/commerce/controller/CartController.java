package it.objectmethod.e.commerce.controller;

import java.util.ArrayList;
import java.util.Optional;

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
import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.repository.CartDetailRepository;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.UtenteRepository;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private ArticoloRepository artRep;
	@Autowired
	private UtenteRepository uteRep;
	@Autowired
	private CartRepository carRep;
	@Autowired
	private CartDetailRepository carDetRep;

	@GetMapping("/aggiungi")
	public ResponseEntity<Cart> aggiungiProdotto(@RequestParam("qta") Integer qta,
			@RequestParam("id_art") Integer idArticolo, @RequestParam("user") Long idUtente) {
		ResponseEntity<Cart> resp = null;
		Utente user = uteRep.findById(idUtente).get();
		Optional<Articolo> optArt = artRep.findById(idArticolo);

		if (optArt.isPresent()) {
			Articolo art = optArt.get();
			int dispAggiornata = art.getDisponibilita() - qta;
			if (dispAggiornata >= 0) {

				Cart carrello = carRep.findByProprietarioCarrelloIdUtente(idUtente);
				boolean existingCart = true;
				if (carrello == null && qta > 0) {
					carrello = new Cart();
					carrello.setProprietarioCarrello(user);
					carrello.setListaSpesa(new ArrayList<CartDetail>());
					existingCart = false;
				} else {
					if (carrello == null && qta < 0) {
						existingCart = false;
					}
				}

				boolean detailNotFound = true;
				if (existingCart) {
					for (CartDetail detailPresente : carrello.getListaSpesa()) {
						if (detailPresente.getArticolo().getIdArticolo().equals(art.getIdArticolo())
								&& detailPresente.getQuantita() + qta >= 0) {
							detailPresente.setQuantita(detailPresente.getQuantita() + qta);
							if (detailPresente.getQuantita() == 0) {
								carDetRep.delete(detailPresente);
								carrello.getListaSpesa().remove(detailPresente);
							} else {
								detailPresente = carDetRep.save(detailPresente);
							}
							detailNotFound = false;
							resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
							art.setDisponibilita(dispAggiornata);
							break;
						}
					}
				}

				if (detailNotFound && qta > 0) {
					CartDetail newDetail = new CartDetail();
					newDetail.setArticolo(art);
					newDetail.setQuantita(qta);
					newDetail = carDetRep.save(newDetail);
					carrello.getListaSpesa().add(newDetail);
					resp = new ResponseEntity<Cart>(carrello, HttpStatus.OK);
					art.setDisponibilita(dispAggiornata);
				} else {
					if (detailNotFound && qta < 0) {
						resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
					}
				}

				if (carrello.getListaSpesa().isEmpty()) {
					carRep.delete(carrello);
				} else {
					carrello = carRep.save(carrello);
				}

			} else {
				resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
			}

		} else {
			resp = new ResponseEntity<Cart>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}
}
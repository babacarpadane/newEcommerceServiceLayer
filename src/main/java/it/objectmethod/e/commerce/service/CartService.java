package it.objectmethod.e.commerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.UtenteRepository;
import it.objectmethod.e.commerce.service.dto.CartDTO;
import it.objectmethod.e.commerce.service.mapper.CartMapper;

@Component
public class CartService {
	@Autowired
	private CartRepository carRep;
	@Autowired
	private UtenteRepository uteRep;
	@Autowired
	private CartMapper carMap;
	@Autowired
	private ArticoloRepository artRep;
	@Autowired
	private JWTService jwtSer;

	public ResponseEntity<CartDTO> aggiungiProdotto(Integer qta, Integer idArticolo, String token) {
		ResponseEntity<CartDTO> resp = null;
		Optional<Articolo> optArt = artRep.findById(idArticolo);

		if (optArt.isPresent() && qta > 0) {
			String nomeUtente = jwtSer.getUsername(token);
			Utente user = uteRep.findByNomeUtente(nomeUtente).get();
			Articolo art = optArt.get();
			int dispAggiornata = art.getDisponibilita() - qta;

			if (dispAggiornata >= 0) {
				art.setDisponibilita(dispAggiornata);
				art = artRep.save(art);

				Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);
				if (carrello == null) {
					carrello = new Cart();
					carrello.setProprietarioCarrello(user);
					carrello.setListaSpesa(new ArrayList<CartDetail>());
				}

				boolean detailNotFound = true;
				if (!carrello.getListaSpesa().isEmpty()) {
					for (CartDetail detailPresente : carrello.getListaSpesa()) {
						if (detailPresente.getArticolo().getIdArticolo().equals(art.getIdArticolo())) {
							detailPresente.setQuantita(detailPresente.getQuantita() + qta);
							detailNotFound = false;
							break;
						}
					}
				}

				if (detailNotFound) {
					CartDetail newDetail = new CartDetail();
					newDetail.setArticolo(art);
					newDetail.setQuantita(qta);
					carrello.getListaSpesa().add(newDetail);
				}

				carrello = carRep.save(carrello);
				CartDTO carrelloDto = carMap.toDto(carrello);

				resp = new ResponseEntity<CartDTO>(carrelloDto, HttpStatus.OK);

			} else {
				resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
			}

		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	public ResponseEntity<CartDTO> rimuoviProdotto(Integer idArticolo, String token) {
		ResponseEntity<CartDTO> resp = null;
		Articolo art = artRep.findById(idArticolo).get();
		String nomeUtente = jwtSer.getUsername(token);
		Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);

		if (carrello != null && !carrello.getListaSpesa().isEmpty() && art != null) {
			for (CartDetail detail : carrello.getListaSpesa()) {
				if (detail.getArticolo().getIdArticolo().equals(art.getIdArticolo())) {
					art.setDisponibilita(art.getDisponibilita() + detail.getQuantita());
					carrello.getListaSpesa().remove(detail);
					carrello = carRep.save(carrello);
					CartDTO carrelloDto = carMap.toDto(carrello);
					resp = new ResponseEntity<CartDTO>(carrelloDto, HttpStatus.OK);
					break;
				}
			}
		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

}

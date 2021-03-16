package it.objectmethod.e.commerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.UtenteRepository;
import it.objectmethod.e.commerce.service.dto.CartDTO;
import it.objectmethod.e.commerce.service.mapper.CartMapper;

@Service
public class CartService {
	@Autowired
	private CartRepository carRep;
	@Autowired
	private UtenteRepository uteRep;
	@Autowired
	private CartMapper carMap;
	@Autowired
	private ArticoloRepository artRep;

	public CartDTO aggiungiProdotto(Integer qta, Integer idArticolo, String nomeUtente) {
		CartDTO carrelloDto = null;
		Optional<Articolo> optArt = artRep.findById(idArticolo);

		if (optArt.isPresent() && qta > 0) {
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
				carrelloDto = carMap.toDto(carrello);

			} else {
				System.out.println("Quantità disponibile inferiore alla quantità richiesta");
			}

		} else {
			System.out.println("Articolo non presente");
		}
		return carrelloDto;
	}

	public CartDTO rimuoviProdotto(Integer idArticolo, String nomeUtente) {
		CartDTO carrelloDto = null;
		Articolo art = artRep.findById(idArticolo).get();
		Cart carrello = carRep.findByProprietarioCarrelloNomeUtente(nomeUtente);

		if (carrello != null && !carrello.getListaSpesa().isEmpty() && art != null) {
			for (CartDetail detail : carrello.getListaSpesa()) {
				if (detail.getArticolo().getIdArticolo().equals(art.getIdArticolo())) {
					art.setDisponibilita(art.getDisponibilita() + detail.getQuantita());
					carrello.getListaSpesa().remove(detail);
					carrello = carRep.save(carrello);
					carrelloDto = carMap.toDto(carrello);
					break;
				}
			}
		} else {
			System.out.println("ERRORE ");
		}
		return carrelloDto;
	}

}

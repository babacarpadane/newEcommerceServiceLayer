package it.objectmethod.e.commerce.service;

import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	private static final Logger logger = LogManager.getLogger(CartService.class);

	public CartDTO aggiungiProdotto(Integer qta, Integer idArticolo, Long idUtente) {
		CartDTO carrelloDto = null;
		Optional<Articolo> optArt = artRep.findById(idArticolo);

		if (optArt.isPresent() && qta > 0) {
			Utente user = uteRep.findById(idUtente).get();
			Articolo art = optArt.get();
			int dispAggiornata = art.getDisponibilita() - qta;

			if (dispAggiornata >= 0) {
				art.setDisponibilita(dispAggiornata);
				art = artRep.save(art);

				Cart carrello = carRep.findByProprietarioCarrelloIdUtente(idUtente);
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
				logger.info("Quantità richiesta superiore alla quantità disponibile");
			}

		} else {
			logger.info("Articolo inesistente");
		}
		return carrelloDto;
	}

	public CartDTO rimuoviProdotto(Integer idArticolo, Long idUtente) {
		CartDTO carrelloDto = null;
		Articolo art = null;
		try {
			art = artRep.findById(idArticolo).get();
		} catch (Exception e) {
			logger.error("Articolo con id specificato inesistente", e);
		}
		Cart carrello = carRep.findByProprietarioCarrelloIdUtente(idUtente);
		if (art != null) {
			if (carrello != null && !carrello.getListaSpesa().isEmpty()) {
				boolean detailNotFound = true;
				for (CartDetail detail : carrello.getListaSpesa()) {
					if (detail.getArticolo().getIdArticolo().equals(art.getIdArticolo())) {
						art.setDisponibilita(art.getDisponibilita() + detail.getQuantita());
						carrello.getListaSpesa().remove(detail);
						carrello = carRep.save(carrello);
						carrelloDto = carMap.toDto(carrello);
						detailNotFound = false;
						break;
					}
				}
				if (detailNotFound) {
					logger.info("Articolo non presente nel carrello");
				}
			} else {
				logger.info("Carrello vuoto o inesistente");
			}
		} else {
			logger.info("Articolo inesistente");
		}
		return carrelloDto;
	}

}

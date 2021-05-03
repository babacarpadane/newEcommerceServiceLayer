package it.objectmethod.e.commerce.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.UtenteRepository;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;
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
	@Autowired
	private Environment environment;

	private static final Logger logger = LogManager.getLogger(CartService.class);

	public CartDTO aggiungiProdotto(Integer qta, Long idArticolo, Long idUtente) {
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
				logger.error("Quantità richiesta superiore alla quantità disponibile");
			}

		} else {
			logger.error("Articolo inesistente");
		}
		return carrelloDto;
	}

	public CartDTO rimuoviProdotto(Long idArticolo, Long idUtente) {
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
					logger.error("Articolo non presente nel carrello");
				}
			} else {
				logger.error("Carrello vuoto o inesistente");
			}
		} else {
			logger.error("Articolo inesistente");
		}
		return carrelloDto;
	}

	public ArticoloDTO[] articoliDisponibili() {
		ArticoloDTO[] listaArticoli = null;

		String ipAddress = null;
		try {
			ipAddress = InetAddress.getLocalHost().getHostAddress(); // "192.168.1.90"
		} catch (UnknownHostException e) {
			logger.error("Non è stato possibile ottenere l'indirizzo IP", e);
		}
		String port = environment.getProperty("local.server.port");
		String url = "http://" + ipAddress + ":" + port + "/api/articolo";

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		listaArticoli = restTemplate.getForObject(url, ArticoloDTO[].class);

//      Secondo metodo:
//		CloseableHttpClient chiamata = HttpClients.createDefault();
//		HttpGet httpGet = new HttpGet(url);
//		CloseableHttpResponse response = null;
//		try {
//			response = chiamata.execute(httpGet);
//			String articoli = IOUtils.toString(response.getEntity().getContent(), StandardCharsets.UTF_8);
//			Gson gson = new Gson();
//			listaArticoli = gson.fromJson(articoli, ArticoloDTO[].class);
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				response.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}

		return listaArticoli;
	}
}

package it.objectmethod.e.commerce.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Ordine;
import it.objectmethod.e.commerce.entity.RigaOrdine;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.OrdineRepository;
import it.objectmethod.e.commerce.service.dto.OrdineDTO;
import it.objectmethod.e.commerce.service.mapper.OrdineMapper;

@Service
public class OrdineService {
	@Autowired
	private CartRepository carRep;
	@Autowired
	private OrdineRepository ordRep;
	@Autowired
	private OrdineMapper ordMap;
	
	private static final Logger logger = LogManager.getLogger(CartService.class);

	public OrdineDTO generaOrdine(Long idUtente) {
		OrdineDTO ordineDto = null;
		Cart carrello = carRep.findByProprietarioCarrelloIdUtente(idUtente);
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
			ordineDto = ordMap.toDto(ordine);
			carrello.getListaSpesa().removeAll(carrello.getListaSpesa());
			carrello = carRep.save(carrello);
		} else {
			logger.info("ERRORE : CARRELLO VUOTO, IMPOSSIBILE GENERARE ORDINE");
		}
		return ordineDto;
	}
}

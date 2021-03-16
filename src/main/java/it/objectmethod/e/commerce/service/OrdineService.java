package it.objectmethod.e.commerce.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.entity.CartDetail;
import it.objectmethod.e.commerce.entity.Ordine;
import it.objectmethod.e.commerce.entity.RigaOrdine;
import it.objectmethod.e.commerce.repository.CartRepository;
import it.objectmethod.e.commerce.repository.OrdineRepository;
import it.objectmethod.e.commerce.service.dto.OrdineDTO;
import it.objectmethod.e.commerce.service.mapper.OrdineMapper;

@Component
public class OrdineService {
	@Autowired
	private CartRepository carRep;
	@Autowired
	private OrdineRepository ordRep;
	@Autowired
	private OrdineMapper ordMap;

	public ResponseEntity<OrdineDTO> generaOrdine(String nomeUtente) {
		ResponseEntity<OrdineDTO> resp = null;
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
			OrdineDTO ordineDto = ordMap.toDto(ordine);
			carrello.getListaSpesa().removeAll(carrello.getListaSpesa());
			carrello = carRep.save(carrello);
			resp = new ResponseEntity<OrdineDTO>(ordineDto, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<OrdineDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
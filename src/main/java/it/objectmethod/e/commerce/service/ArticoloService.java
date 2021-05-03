package it.objectmethod.e.commerce.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;
import it.objectmethod.e.commerce.service.mapper.ArticoloMapper;

@Service
public class ArticoloService {
	@Autowired
	private ArticoloRepository repArticolo;
	@Autowired
	ArticoloMapper artMap;
	private static final Logger logger = LogManager.getLogger(ArticoloService.class);

	public ArticoloDTO findArticoloById(Long id) {
		ArticoloDTO articoloTrovatoDto = null;
		try {
			Articolo articoloTrovato = repArticolo.findById(id).get();
			articoloTrovatoDto = artMap.toDto(articoloTrovato);
		} catch (Exception e) {
			logger.error("ID dell'articolo errato", e);
		}
		return articoloTrovatoDto;
	}

	public List<ArticoloDTO> findAll() {
		List<ArticoloDTO> articoliTrovatiDto = null;
		try {
			List<Articolo> articoliTrovati = repArticolo.findAll();
			articoliTrovatiDto = artMap.toDto(articoliTrovati);
		} catch (Exception e) {
			logger.error("Errore", e);
		}
		return articoliTrovatiDto;
	}

//	public List<ArticoloDTO> findArticoliByNameOrCode(String name, String codiceArticolo) {
//		List<ArticoloDTO> articoliTrovatiDto = null;
//		try {
//			List<Articolo> articoliTrovati = repArticolo.trovaArticoli(name, codiceArticolo);
//			articoliTrovatiDto = artMap.toDto(articoliTrovati);
//		} catch (Exception e) {
//			logger.error("Codice dell'articolo o nome articolo errati", e);
//		}
//		return articoliTrovatiDto;
//	}

}

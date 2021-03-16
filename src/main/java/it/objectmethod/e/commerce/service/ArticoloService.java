package it.objectmethod.e.commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;

@Service
public class ArticoloService {
	@Autowired
	private ArticoloRepository repArticolo;

	public List<ArticoloDTO> findArticoliByNameOrCode(String name, String codiceArticolo) {
		List<ArticoloDTO> articoliTrovati = repArticolo.trovaArticoli(name, codiceArticolo);
		return articoliTrovati;
	}
}

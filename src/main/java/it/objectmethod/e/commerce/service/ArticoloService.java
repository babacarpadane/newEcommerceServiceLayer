package it.objectmethod.e.commerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.objectmethod.e.commerce.repository.ArticoloRepository;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;

@Component
public class ArticoloService {
	@Autowired
	private ArticoloRepository repArticolo;

	public ResponseEntity<List<ArticoloDTO>> findArticoliByNameOrCode(String name, String codiceArticolo) {
		ResponseEntity<List<ArticoloDTO>> resp = null;
		List<ArticoloDTO> articoliTrovati = repArticolo.trovaArticoli(name, codiceArticolo);
		if (!articoliTrovati.isEmpty()) {
			resp = new ResponseEntity<List<ArticoloDTO>>(articoliTrovati, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<List<ArticoloDTO>>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}

package it.objectmethod.e.commerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.ArticoloService;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;

@RestController
@RequestMapping("/api/articolo")
public class ArticoloController {
	@Autowired
	private ArticoloService artSer;

	@Transactional
	@GetMapping(value = { "", "/{idArticolo}" })
	public ResponseEntity<List<ArticoloDTO>> findArticoloById(@PathVariable(required = false) Long idArticolo) {
		ResponseEntity<List<ArticoloDTO>> resp = null;

		if (idArticolo == null) {
			List<ArticoloDTO> articoli = artSer.findAll();
			if (!articoli.isEmpty()) {
				resp = new ResponseEntity<List<ArticoloDTO>>(articoli, HttpStatus.OK);
			} else {
				resp = new ResponseEntity<List<ArticoloDTO>>(HttpStatus.BAD_REQUEST);
			}
		} else {
			ArticoloDTO articolo = artSer.findArticoloById(idArticolo);
			List<ArticoloDTO> list = new ArrayList<ArticoloDTO>();
			list.add(articolo);
			if (!list.isEmpty()) {
				resp = new ResponseEntity<List<ArticoloDTO>>(list, HttpStatus.OK);
			} else {
				resp = new ResponseEntity<List<ArticoloDTO>>(HttpStatus.BAD_REQUEST);
			}
		}

		return resp;
	}

//	@Transactional
//	@GetMapping("/trova-articoli")
//	public ResponseEntity<List<ArticoloDTO>> findArticoliByNameOrCode(@RequestParam("name") String name,
//			@RequestParam("codiceArticolo") String codiceArticolo) {
//		ResponseEntity<List<ArticoloDTO>> resp = null;
//		List<ArticoloDTO> articoli = artSer.findArticoliByNameOrCode(name, codiceArticolo);
//		if (!articoli.isEmpty()) {
//			resp = new ResponseEntity<List<ArticoloDTO>>(articoli, HttpStatus.OK);
//		} else {
//			resp = new ResponseEntity<List<ArticoloDTO>>(HttpStatus.BAD_REQUEST);
//		}
//		return resp;
//	}

}
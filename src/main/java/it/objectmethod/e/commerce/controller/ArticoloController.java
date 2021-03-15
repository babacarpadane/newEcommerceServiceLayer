package it.objectmethod.e.commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.ArticoloService;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;

@RestController
@RequestMapping("/api/articolo")
public class ArticoloController {
	@Autowired
	private ArticoloService artSer;

	@GetMapping("/trova-articoli")
	public ResponseEntity<List<ArticoloDTO>> findArticoliByNameOrCode(@RequestParam("name") String name,
			@RequestParam("codiceArticolo") String codiceArticolo) {
		ResponseEntity<List<ArticoloDTO>> resp = artSer.findArticoliByNameOrCode(name, codiceArticolo);
		return resp;
	}
}
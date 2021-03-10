package it.objectmethod.e.commerce.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.repository.ArticoloRepository;

@RestController
@RequestMapping("/api/articolo")
public class ArticoloController {
	@Autowired
	private ArticoloRepository repArticolo;

	@GetMapping("/trova-articoli")
	public ResponseEntity<ArrayList<Articolo>> findArticoliByNameOrCode(@RequestParam("name") String name,
			@RequestParam("codiceArticolo") String codiceArticolo) {
		ResponseEntity<ArrayList<Articolo>> resp = null;
		ArrayList<Articolo> articoliTrovati = repArticolo.trovaArticoli(name, codiceArticolo);
		if (!articoliTrovati.isEmpty()) {
			resp = new ResponseEntity<ArrayList<Articolo>>(articoliTrovati, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<ArrayList<Articolo>>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
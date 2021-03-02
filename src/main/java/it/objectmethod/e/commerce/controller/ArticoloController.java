package it.objectmethod.e.commerce.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Articolo> findArticoliByNameOrCode(@RequestParam("name") String name,
			@RequestParam("codiceArticolo") String codiceArticolo) {
		ArrayList<Articolo> articoliTrovato = repArticolo.trovaArticoli(name, codiceArticolo);
		return articoliTrovato;
	}

}
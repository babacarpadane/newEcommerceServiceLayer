package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.UtenteRepository;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	@Autowired
	private UtenteRepository repUtente;

	@GetMapping("/login")
	public Utente login(@RequestParam("username") String username, @RequestParam("password") String password) {
		Utente utenteLoggato = repUtente.findByNomeUtenteAndPassword(username, password);
		return utenteLoggato;
	}

}
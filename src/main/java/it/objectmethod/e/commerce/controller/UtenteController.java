package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.controller.service.JWTService;
import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.UtenteRepository;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	@Autowired
	private UtenteRepository repUtente;
	@Autowired
	private JWTService jwtSer;

	@GetMapping("/login")
	public ResponseEntity<Utente> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		ResponseEntity<Utente> resp = null;
		Utente utenteLoggato = repUtente.findByNomeUtenteAndPassword(username, password);
		if (utenteLoggato != null) {
			String token = jwtSer.generateJWTToken(utenteLoggato);
			System.out.println("Token: " + token);
			resp = new ResponseEntity<Utente>(utenteLoggato, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<Utente>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
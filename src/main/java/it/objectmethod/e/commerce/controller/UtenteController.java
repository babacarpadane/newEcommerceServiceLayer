package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.UtenteRepository;
import it.objectmethod.e.commerce.service.JWTService;
import it.objectmethod.e.commerce.service.UtenteService;
import it.objectmethod.e.commerce.service.dto.UtenteDTO;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	@Autowired
	private UtenteService uteSer;
	@Autowired
	private JWTService jwtSer;
	@Autowired
	private UtenteRepository repUtente;

	@GetMapping("/login")
	public ResponseEntity<UtenteDTO> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		ResponseEntity<UtenteDTO> resp = null;
		Utente utenteLoggato = repUtente.findByNomeUtenteAndPassword(username, password);
		if (utenteLoggato != null) {
			UtenteDTO finalUtente = uteSer.convertToDto(utenteLoggato);
			resp = new ResponseEntity<UtenteDTO>(finalUtente, HttpStatus.OK);
			String token = jwtSer.generateJWTToken(utenteLoggato);
			System.out.println("Token: " + token);
		} else {
			resp = new ResponseEntity<UtenteDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
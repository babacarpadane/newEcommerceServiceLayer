package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.JWTService;

@RestController
@RequestMapping("/api/utente")
public class UtenteController {
	@Autowired
	private JWTService jwtSer;

	@GetMapping("/login")
	public ResponseEntity<String> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		ResponseEntity<String> resp = null;
		String token = jwtSer.generateJWTToken(username, password);
		if (token != null) {
			resp = new ResponseEntity<String>(token, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.JWTService;
import it.objectmethod.e.commerce.service.OrdineService;
import it.objectmethod.e.commerce.service.dto.OrdineDTO;

@RestController
@RequestMapping("/api/ordine")
public class OrdineController {
	@Autowired
	private OrdineService ordSer;
	@Autowired
	private JWTService jwtSer;

	@PostMapping("/genera-ordine")
	public ResponseEntity<OrdineDTO> stampaOrdine(@RequestHeader("authentificationToken") String token) {
		String nomeUtente = jwtSer.getUsername(token); // si prende il token da solo
		ResponseEntity<OrdineDTO> resp = ordSer.generaOrdine(nomeUtente);
		return resp;
	}
}
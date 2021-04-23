package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
	
	@Transactional
	@PostMapping("/genera-ordine")
	public ResponseEntity<OrdineDTO> stampaOrdine(@RequestHeader("authentificationToken") String token) {
		Long idUtente = jwtSer.getIdUtente(token);
		OrdineDTO order = ordSer.generaOrdine(idUtente);
		ResponseEntity<OrdineDTO> resp = null;
		if (order != null) {
			resp = new ResponseEntity<OrdineDTO>(order, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<OrdineDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}
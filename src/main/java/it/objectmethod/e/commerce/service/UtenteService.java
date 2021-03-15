package it.objectmethod.e.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.UtenteRepository;
import it.objectmethod.e.commerce.service.dto.UtenteDTO;
import it.objectmethod.e.commerce.service.mapper.UtenteMapper;

@Component
public class UtenteService {
	@Autowired
	private UtenteRepository repUtente;
	@Autowired
	private UtenteMapper uteMap;
	@Autowired
	private JWTService jwtSer;

	public ResponseEntity<UtenteDTO> login(String username, String password) {
		ResponseEntity<UtenteDTO> resp = null;
		Utente utenteLoggato = repUtente.findByNomeUtenteAndPassword(username, password);
		if (utenteLoggato != null) {
			String token = jwtSer.generateJWTToken(utenteLoggato);
			UtenteDTO utenteLoggatoDto = uteMap.toDto(utenteLoggato);
			System.out.println("Token: " + token);
			resp = new ResponseEntity<UtenteDTO>(utenteLoggatoDto, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<UtenteDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}
}

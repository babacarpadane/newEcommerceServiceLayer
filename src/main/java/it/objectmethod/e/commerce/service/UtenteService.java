package it.objectmethod.e.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.service.dto.UtenteDTO;
import it.objectmethod.e.commerce.service.mapper.UtenteMapper;

@Component
public class UtenteService {
	@Autowired
	private UtenteMapper uteMap;

	public ResponseEntity<UtenteDTO> login(Utente utenteLoggato) {
		ResponseEntity<UtenteDTO> resp = null;
		UtenteDTO utenteLoggatoDto = uteMap.toDto(utenteLoggato);
		resp = new ResponseEntity<UtenteDTO>(utenteLoggatoDto, HttpStatus.OK);

		return resp;
	}
}

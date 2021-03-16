package it.objectmethod.e.commerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.service.dto.UtenteDTO;
import it.objectmethod.e.commerce.service.mapper.UtenteMapper;

@Service
public class UtenteService {
	@Autowired
	private UtenteMapper uteMap;

	public UtenteDTO convertToDto(Utente utenteLoggato) {
		UtenteDTO utenteLoggatoDto = uteMap.toDto(utenteLoggato);
		return utenteLoggatoDto;
	}
}

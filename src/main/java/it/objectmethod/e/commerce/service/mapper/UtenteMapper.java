package it.objectmethod.e.commerce.service.mapper;

import org.mapstruct.Mapper;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.service.dto.UtenteDTO;

@Mapper(componentModel = "spring")
public interface UtenteMapper extends Entitymapper<UtenteDTO, Utente> {
	@Override
	UtenteDTO toDto(Utente entity);

	@Override
	Utente toEntity(UtenteDTO dto);
}

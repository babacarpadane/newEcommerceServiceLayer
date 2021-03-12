package it.objectmethod.e.commerce.controller.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.controller.service.dto.UtenteDTO;
import it.objectmethod.e.commerce.entity.Utente;

@Mapper(componentModel = "spring")
public interface UtenteMapper extends Entitymapper<UtenteDTO, Utente> {
	@Override
	UtenteDTO toDto(Utente entity);

	@Override
	@Mapping(target = "password", ignore = true)
	Utente toEntity(UtenteDTO dto);
}

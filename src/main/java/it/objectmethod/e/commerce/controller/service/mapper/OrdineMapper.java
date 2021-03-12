package it.objectmethod.e.commerce.controller.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.controller.service.dto.OrdineDTO;
import it.objectmethod.e.commerce.entity.Ordine;

@Mapper(componentModel = "spring")
public interface OrdineMapper extends Entitymapper<OrdineDTO, Ordine> {
	@Override
	@Mapping(source = "utente.idUtente", target = "idUtente")
	@Mapping(source = "utente.nomeUtente", target = "nomeUtente")
	OrdineDTO toDto(Ordine entity);

	@Override
	@Mapping(target = "proprietarioOrdine", ignore = true)
	@Mapping(target = "righeOrdine", ignore = true)
	Ordine toEntity(OrdineDTO dto);
}

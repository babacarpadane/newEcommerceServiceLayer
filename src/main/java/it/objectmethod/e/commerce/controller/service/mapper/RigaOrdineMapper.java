package it.objectmethod.e.commerce.controller.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.controller.service.dto.RigaOrdineDTO;
import it.objectmethod.e.commerce.entity.RigaOrdine;

@Mapper(componentModel = "spring")
public interface RigaOrdineMapper extends Entitymapper<RigaOrdineDTO, RigaOrdine> {
	@Override
	@Mapping(source = "articolo.idArticolo", target = "idArticolo")
	@Mapping(source = "articolo.codiceArticolo", target = "codiceArticolo")
	@Mapping(source = "articolo.nomeArticolo", target = "nomeArticolo")
	RigaOrdineDTO toDto(RigaOrdine entity);

	@Override
	@Mapping(target = "articolo", ignore = true)
	RigaOrdine toEntity(RigaOrdineDTO dto);
}

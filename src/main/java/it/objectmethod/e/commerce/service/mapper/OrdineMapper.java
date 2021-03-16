package it.objectmethod.e.commerce.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.entity.Ordine;
import it.objectmethod.e.commerce.service.dto.OrdineDTO;

@Mapper(componentModel = "spring", uses = { RigaOrdineMapper.class })
public interface OrdineMapper extends Entitymapper<OrdineDTO, Ordine> {
	@Override
	@Mapping(source = "proprietarioOrdine.idUtente", target = "idUtente")
	@Mapping(source = "proprietarioOrdine.nomeUtente", target = "nomeUtente")
	OrdineDTO toDto(Ordine entity);

	@Override
	@Mapping(target = "proprietarioOrdine", ignore = true)
	Ordine toEntity(OrdineDTO dto);
}

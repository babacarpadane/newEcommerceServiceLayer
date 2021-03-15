package it.objectmethod.e.commerce.service.mapper;

import org.mapstruct.Mapper;

import it.objectmethod.e.commerce.entity.Articolo;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;

@Mapper(componentModel = "spring")
public interface ArticoloMapper extends Entitymapper<ArticoloDTO, Articolo>{

	@Override
	ArticoloDTO toDto (Articolo entity);
	@Override
	Articolo toEntity (ArticoloDTO dto);

}

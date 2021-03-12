package it.objectmethod.e.commerce.controller.service.mapper;


import org.mapstruct.Mapper;

import it.objectmethod.e.commerce.controller.service.dto.ArticoloDTO;
import it.objectmethod.e.commerce.entity.Articolo;

@Mapper(componentModel = "spring")
public interface ArticoloMapper extends Entitymapper<ArticoloDTO, Articolo>{

	@Override
	ArticoloDTO toDto (Articolo entity);
	@Override
	Articolo toEntity (ArticoloDTO dto);

}

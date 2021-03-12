package it.objectmethod.e.commerce.controller.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.controller.service.dto.CartDetailDTO;
import it.objectmethod.e.commerce.entity.CartDetail;

@Mapper(componentModel = "spring")
public interface CartDetailMapper extends Entitymapper<CartDetailDTO, CartDetail> {
	@Override
	@Mapping(source = "articolo.idArticolo", target = "idArticolo")
	@Mapping(source = "articolo.codiceArticolo", target = "codiceArticolo")
	@Mapping(source = "articolo.nomeArticolo", target = "nomeArticolo")
	CartDetailDTO toDto(CartDetail entity); // mappa da entity a dto

	@Override
	@Mapping(target = "articolo", ignore = true)
	CartDetail toEntity(CartDetailDTO dto); // mappa da dto a entity

}

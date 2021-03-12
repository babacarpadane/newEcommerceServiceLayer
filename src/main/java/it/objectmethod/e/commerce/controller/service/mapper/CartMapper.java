package it.objectmethod.e.commerce.controller.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.controller.service.dto.CartDTO;
import it.objectmethod.e.commerce.entity.Cart;

@Mapper(componentModel = "spring")
public interface CartMapper extends Entitymapper<CartDTO, Cart> {
	@Override
	@Mapping(source = "utente.idUtente", target = "idUtente")
	@Mapping(source = "utente.nomeUtente", target = "nomeUtente")
	CartDTO toDto(Cart entity);

	@Override
	@Mapping(target = "proprietarioCarrello", ignore = true)
	@Mapping(target = "listaSpesa", ignore = true)
	Cart toEntity(CartDTO dto);
}

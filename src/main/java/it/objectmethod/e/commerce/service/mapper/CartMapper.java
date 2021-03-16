package it.objectmethod.e.commerce.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.service.dto.CartDTO;

@Mapper(componentModel = "spring", uses = { CartDetailMapper.class })
public interface CartMapper extends Entitymapper<CartDTO, Cart> {
	@Override
	@Mapping(source = "proprietarioCarrello.idUtente", target = "idUtente")
	@Mapping(source = "proprietarioCarrello.nomeUtente", target = "nomeUtente")
	CartDTO toDto(Cart entity);

	@Override
	@Mapping(target = "proprietarioCarrello", ignore = true)
	Cart toEntity(CartDTO dto);
}

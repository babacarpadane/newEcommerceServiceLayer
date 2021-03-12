package it.objectmethod.e.commerce.controller.service.mapper;

import java.util.List;

//D dto
//E entity

public interface Entitymapper<D, E> {
	D toDto(E entity);

	List<D> toDto(List<E> entity);

	E toEntity(D dto);

	List<E> toEntity(List<D> entity);
}

package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.RigaOrdine;

@Repository
public interface RigaOrdineRepository extends JpaRepository<RigaOrdine, Long>{
	//QUESTA REPOSITORY NON È MAI USATA
}

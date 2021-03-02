package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.Ordine;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {

}

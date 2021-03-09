package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.Ordine;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
	@Query (nativeQuery = true, value ="SELECT numero_ordine FROM ecommerce.ordine AS o ORDER BY id_ordine DESC LIMIT 1")
	public String findLastNumeroOrdine();
}

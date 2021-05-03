package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.Articolo;

@Repository
public interface ArticoloRepository extends JpaRepository<Articolo, Long> {
//	@Query("select c from Articolo c where (''=?1 or c.nomeArticolo=?1) and (''=?2 or c.codiceArticolo = ?2)")
//	public List<Articolo> trovaArticoli(String name, String codiceArticolo);

}
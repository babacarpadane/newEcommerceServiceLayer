package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.Utente;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
	public Utente findByNomeUtenteAndPassword(String username, String password);
}

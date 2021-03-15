package it.objectmethod.e.commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.objectmethod.e.commerce.entity.Cart;
import it.objectmethod.e.commerce.service.dto.CartDTO;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	public Cart findByProprietarioCarrelloNomeUtente (String nomeUtente);
}
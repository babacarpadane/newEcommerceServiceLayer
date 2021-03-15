package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.CartService;
import it.objectmethod.e.commerce.service.JWTService;
import it.objectmethod.e.commerce.service.dto.CartDTO;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartService carSer;
	@Autowired
	private JWTService jwtSer;

	@GetMapping("/add")
	public ResponseEntity<CartDTO> aggiungiProdotto(@RequestParam("qta") Integer qta,
			@RequestParam("id_art") Integer idArticolo, @RequestHeader("authentificationToken") String token) {
		String nomeUtente = jwtSer.getUsername(token);
		ResponseEntity<CartDTO> resp = carSer.aggiungiProdotto(qta, idArticolo, nomeUtente);
		return resp;
	}

	@GetMapping("/remove")
	public ResponseEntity<CartDTO> rimuoviProdotto(@RequestParam("id_art") Integer idArticolo,
			@RequestHeader("authentificationToken") String token) {
		String nomeUtente = jwtSer.getUsername(token);
		ResponseEntity<CartDTO> resp = carSer.rimuoviProdotto(idArticolo, nomeUtente);
		return resp;
	}
}
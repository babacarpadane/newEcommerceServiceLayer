package it.objectmethod.e.commerce.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.objectmethod.e.commerce.service.CartService;
import it.objectmethod.e.commerce.service.JWTService;
import it.objectmethod.e.commerce.service.dto.ArticoloDTO;
import it.objectmethod.e.commerce.service.dto.CartDTO;

@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired
	private CartService carSer;
	@Autowired
	private JWTService jwtSer;

	@Transactional
	@GetMapping("/add")
	public ResponseEntity<CartDTO> aggiungiProdotto(@RequestParam("qta") Integer qta,
			@RequestParam("id_art") Long idArticolo, @RequestHeader("authentificationToken") String token) {
		Long idUtente = jwtSer.getIdUtente(token);
		CartDTO addedDetCart = carSer.aggiungiProdotto(qta, idArticolo, idUtente);
		ResponseEntity<CartDTO> resp = null;
		if (addedDetCart != null) {
			resp = new ResponseEntity<CartDTO>(addedDetCart, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	@Transactional
	@GetMapping("/remove")
	public ResponseEntity<CartDTO> rimuoviProdotto(@RequestParam("id_art") Long idArticolo,
			@RequestHeader("authentificationToken") String token) {
		Long idUtente = jwtSer.getIdUtente(token);
		CartDTO removedDetCart = carSer.rimuoviProdotto(idArticolo, idUtente);
		ResponseEntity<CartDTO> resp = null;
		if (removedDetCart != null) {
			resp = new ResponseEntity<CartDTO>(removedDetCart, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<CartDTO>(HttpStatus.BAD_REQUEST);
		}
		return resp;
	}

	@Transactional
	@GetMapping("/articoli-disponibili")
	public ResponseEntity<ArticoloDTO[]> mostraArticoliDisponbili(
			@RequestHeader("authentificationToken") String token) {
		ResponseEntity<ArticoloDTO[]> resp = null;
		ArticoloDTO[] listaArticoli = carSer.articoliDisponibili();
		if (listaArticoli.length > 0) {
			resp = new ResponseEntity<ArticoloDTO[]>(listaArticoli, HttpStatus.OK);
		} else {
			resp = new ResponseEntity<ArticoloDTO[]>(HttpStatus.BAD_REQUEST);
		}

		return resp;
	}
}
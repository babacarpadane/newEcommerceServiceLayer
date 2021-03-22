package it.objectmethod.e.commerce.service;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import it.objectmethod.e.commerce.entity.Utente;
import it.objectmethod.e.commerce.repository.UtenteRepository;

@Component
public class JWTService {
	private static final String JWT_KEY = "yd8uNMYQoIm9e5dcAue0ZQZOO34";

	@Autowired
	private UtenteRepository repUtente;

	private static final Logger logger = LogManager.getLogger(JWTService.class);

	public String generateJWTToken(String username, String password) {
		String token = null;
		Utente utenteLoggato = repUtente.findByNomeUtenteAndPassword(username, password);
		if (utenteLoggato != null) {
			Algorithm alg = Algorithm.HMAC256(JWT_KEY);
			Date expirationTime = new Date(System.currentTimeMillis() + 3600000);
			// (Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
			token = JWT.create().withClaim("idUtente", utenteLoggato.getIdUtente())
					.withClaim("username", utenteLoggato.getNomeUtente()).withExpiresAt(expirationTime).sign(alg);
		} else {
			logger.error("Username o password errati ");
		}
		return token;
	}

	public boolean verifyToken(String token) {
		boolean valid = false;
		try {
			decodeToken(token);
			valid = true;
		} catch (Exception e) {
			logger.error("Verifica del token fallita", e);
		}
		return valid;
	}

	public Long getIdUtente(String token) {
		DecodedJWT decodedToken = decodeToken(token);
		Long idUtente = decodedToken.getClaim("idUtente").asLong();
		return idUtente;
	}

	private DecodedJWT decodeToken(String token) {
		Algorithm alg = Algorithm.HMAC256(JWT_KEY);
		JWTVerifier verify = JWT.require(alg).build();
		DecodedJWT decodedToken = verify.verify(token);
		return decodedToken;
	}

}
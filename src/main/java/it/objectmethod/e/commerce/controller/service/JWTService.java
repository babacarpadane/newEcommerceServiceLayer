package it.objectmethod.e.commerce.controller.service;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import it.objectmethod.e.commerce.entity.Utente;

@Component
public class JWTService {
	private static final String JWT_KEY = "yd8uNMYQoIm9e5dcAue0ZQZOO34";

	public String generateJWTToken(Utente utente) {
		Algorithm alg = Algorithm.HMAC256(JWT_KEY);
		Date expirationTime = new Date(System.currentTimeMillis() + 3600000);
		// (Date.from(ZonedDateTime.now().plusDays(1).toInstant()))
		String token = JWT.create().withClaim("username", utente.getNomeUtente())
				.withClaim("password", utente.getPassword()).withExpiresAt(expirationTime).sign(alg);
		return token;
	}

	public boolean verifyToken(String token) {
		boolean valid = false;
		Algorithm alg = Algorithm.HMAC256(JWT_KEY);
		try {
			JWTVerifier verify = JWT.require(alg).build();
			verify.verify(token);
			valid = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return valid;
	}

	public String getUsername(String token) {
		Algorithm alg = Algorithm.HMAC256(JWT_KEY);
		JWTVerifier verify = JWT.require(alg).build();
		DecodedJWT decodedToken = verify.verify(token);
		String username = decodedToken.getClaim("username").asString();

		return username;
	}
}
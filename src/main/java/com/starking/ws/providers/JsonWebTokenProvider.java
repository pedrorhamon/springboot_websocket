package com.starking.ws.providers;

import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
public class JsonWebTokenProvider implements TokenProvider{
	
	@Autowired
	private KeyProvider keyProvider;

	@Override
	public Map<String, String> decoder(String token) {
		DecodedJWT jwt = JWT.decode(token);
		PublicKey publicKey = keyProvider.getPublicKey(jwt.getKeyId());
		Algorithm algorithm = Algorithm.RSA256((RSAPublicKey) publicKey, null);
		algorithm.verify(jwt);
		boolean expire = jwt
				.getExpiresAtAsInstant()
				.atZone(ZoneId.systemDefault())
				.isBefore(ZonedDateTime.now());
		if(expire) throw new RuntimeException("token is expired");
		return Map.of("id", jwt.getSubject(),
				"name",jwt.getClaim("name").asString(),
				"picture", jwt.getClaim("picture").asString());
	}

}

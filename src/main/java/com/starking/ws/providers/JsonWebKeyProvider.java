package com.starking.ws.providers;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.PublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.UrlJwkProvider;

@Component
public class JsonWebKeyProvider implements KeyProvider{
	
	private final UrlJwkProvider provider;
	
	public JsonWebKeyProvider(@Value("${app.auth.jwks-url}") final String jwkUrl) {
		try {
			this.provider = new UrlJwkProvider(new URL(jwkUrl));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	@Cacheable("public-key")
	@Override
	public PublicKey getPublicKey(String keyId) {
		try {
			final Jwk jwk = provider.get(keyId);
			return jwk.getPublicKey();
		} catch (JwkException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}

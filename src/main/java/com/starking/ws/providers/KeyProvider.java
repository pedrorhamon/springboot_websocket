package com.starking.ws.providers;

import java.security.PublicKey;

public interface KeyProvider {

	PublicKey getPublicKey(String keyId);
}

package com.starking.ws.providers;

import java.util.Map;

public interface TokenProvider {
	Map<String, String> decoder(String token);
}

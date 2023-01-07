package com.starking.ws.providers;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonWebTokenProviderTest {
	
	@Autowired
	private TokenProvider provider;
	
	@Test
	public void test() {
		Map<String, String> decoded = provider.decoder("adfdafdadfadad");
		System.out.println("incripto: " + decoded);
	}
}

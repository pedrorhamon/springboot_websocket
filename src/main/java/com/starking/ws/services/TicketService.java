package com.starking.ws.services;

import java.time.Duration;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.starking.ws.providers.TokenProvider;

@Service
public class TicketService {

	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	private TokenProvider provider;
	
	public String buildAndSaveTicket(String token) {
		if(token == null || token.isBlank()) throw new RuntimeException("missing token");
		String ticket = UUID.randomUUID().toString();
		Map<String, String> user = provider.decoder(token);
		String userId = user.get("id");
		redisTemplate.opsForValue().set(ticket, userId, Duration.ofSeconds(10L));
		return ticket;
	}
	
	public Optional<String> getUserIdByTicket(String ticket) {
		return Optional.ofNullable(redisTemplate.opsForValue().getAndDelete(ticket));
	}
}

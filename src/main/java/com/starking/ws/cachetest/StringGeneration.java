package com.starking.ws.cachetest;

import java.util.UUID;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class StringGeneration {

	public String buildSimpleString() {
		return UUID.randomUUID().toString();
	}
	
	@Cacheable("cached-string")
	public String buildCacheString() {
		return UUID.randomUUID().toString();
	}

}

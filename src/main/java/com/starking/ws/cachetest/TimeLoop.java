package com.starking.ws.cachetest;

import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TimeLoop {
	
	@Autowired
	StringGeneration stringGeneration;
	
	@PostConstruct
	void init() {
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				String sinple = stringGeneration.buildCacheString();
				String cached = stringGeneration.buildCacheString();
				
				System.out.println("sinple: " + sinple + "cached: " + cached);
			}
		}, 2000L, 2000l);
	}

}

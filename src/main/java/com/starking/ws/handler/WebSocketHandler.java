package com.starking.ws.handler;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("[afterConnectionEstablished] session id " + session.getId());
//		super.afterConnectionEstablished(session);
		
		new Timer().scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				try {
					session.sendMessage(new TextMessage("Olá" + UUID.randomUUID()));					
				}catch(IOException e) {
					e.printStackTrace();
				}
			}
		}, 20000L, 20000L);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("[handleTextMessage] message " + message.getPayload());
//		super.handleTextMessage(session, message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("[afterConnectionClosed] session id " + session.getId());
//		super.afterConnectionClosed(session, status);
	}
}

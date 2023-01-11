package com.starking.ws.handler;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.starking.ws.services.TicketService;

@Component
public class WebSocketHandler extends TextWebSocketHandler {
	
	private final static Logger LOGGER = Logger.getLogger(WebSocketHandler.class.getName());
	
	private final TicketService ticketService;
	
	private final Map<String, WebSocketSession> sessions;
	
	public WebSocketHandler(TicketService ticketService) {
		this.ticketService = ticketService;
		sessions = new ConcurrentHashMap<>();
	}
	
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        LOGGER.info("[afterConnectionEstablished] session id " + session.getId());
        Optional<String> ticket = ticketOf(session);
        if(ticket.isEmpty() || ticket.get().isBlank()) {
        	LOGGER.warning("session: " + session.getId() + "without ticket");
        	session.close();
        	return
        }
    }
    
    private void close(WebSocketSession session) {
    	try {
			session.close(CloseStatus.POLICY_VIOLATION);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    private Optional<String> ticketOf(WebSocketSession session) {
    	return Optional.ofNullable(session.getUri())
    			.map(UriComponentsBuilder::fromUri)
    			.map(UriComponentsBuilder::build)
    			.map(UriComponents::getQueryParams)
    			.map(it -> it.get("ticket"))
    			.flatMap(it -> it.stream().findFirst())
    			.map(String::trim);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
    	 LOGGER.info("[handleTextMessage] message " + message.getPayload());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
    	 LOGGER.info("[afterConnectionClosed] session id " + session.getId());
    }
}


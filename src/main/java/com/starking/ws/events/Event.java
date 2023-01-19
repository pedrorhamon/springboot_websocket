package com.starking.ws.events;

public record Event<T>(EventType type, T payload) {
}

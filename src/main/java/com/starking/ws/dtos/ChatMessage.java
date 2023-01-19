package com.starking.ws.dtos;

import com.starking.ws.data.User;

public record ChatMessage(User from, User to, String text) {
}

package com.starking.ws.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.starking.ws.data.User;
import com.starking.ws.data.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findChatUsers(){
        return userRepository.findAll();
    }
}

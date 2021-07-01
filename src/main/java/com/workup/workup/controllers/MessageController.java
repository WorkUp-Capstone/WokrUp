package com.workup.workup.controllers;

import com.workup.workup.dao.UsersRepository;
import com.workup.workup.models.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{to}")
    public void sendMessage(@DestinationVariable String to, MessageModel message){
        System.out.println("Handling send message: " + message + " to: " + to);

        // TODO: need to access user storage on SQL and getInstance method
        // boolean isExists = UsersRepository.getInstance().getUsers().contains(to);
        // if(isExists) {
            // simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
        // }
    }
}

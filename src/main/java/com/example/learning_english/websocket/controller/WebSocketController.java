package com.example.learning_english.websocket.controller;

import com.example.learning_english.websocket.model.ChatMessage;
import com.example.learning_english.websocket.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
public class WebSocketController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/publicChatRoom")
    public List<ChatMessage> sendMessage(@Payload ChatMessage chatMessage){
        chatMessage.setCreateDate(new Date());
        chatMessageRepository.save(chatMessage);

        return chatMessageRepository.findAll(PageRequest.of(0, 5, Sort.Direction.DESC, "createDate")).getContent();
    }

    @MessageMapping("/addUser")
    @SendTo("/topic/publicChatRoom")
    public List<ChatMessage> addUser(@Payload ChatMessage chatMessage){
        return chatMessageRepository.findAll(PageRequest.of(0, 5, Sort.Direction.DESC, "createDate")).getContent();

    }
}


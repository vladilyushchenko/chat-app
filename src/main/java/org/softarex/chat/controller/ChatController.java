package org.softarex.chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.softarex.chat.dto.ChatMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Slf4j
@Controller
public class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessageDto send(String msg) throws IOException {
        ChatMessageDto message = new ObjectMapper().readValue(msg, ChatMessageDto.class);
        log.info("Message in chat received : {}", message);
        return message;
    }
}
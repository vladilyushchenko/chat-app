package org.softarex.chat.service;

import lombok.RequiredArgsConstructor;
import org.softarex.chat.dto.ChatMessageDto;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static org.softarex.chat.constants.ChatConstants.MESSAGES_TOPIC;
import static org.softarex.chat.dto.MessageType.LEAVE;
import static org.softarex.chat.dto.MessageType.NEW_USER;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final SimpMessagingTemplate template;
    private final UserService userService;

    public void handleUserJoin(String username) {
        template.convertAndSend(MESSAGES_TOPIC, new ChatMessageDto(username, NEW_USER));
    }

    public void handleUserLeave(String username) {
        userService.deleteByUsername(username);
        template.convertAndSend(MESSAGES_TOPIC, new ChatMessageDto(username, LEAVE));
    }
}
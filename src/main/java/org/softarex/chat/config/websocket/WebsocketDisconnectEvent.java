package org.softarex.chat.config.websocket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.softarex.chat.service.ChatService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketDisconnectEvent implements ApplicationListener<SessionDisconnectEvent> {

    private final ChatService chatService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        var principal = event.getUser();
        chatService.handleUserLeave(principal.getName());
        log.info("User '{}' disconnected from chat!", principal.getName());
    }
}
package org.softarex.chat.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.softarex.chat.service.ChatService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketConnectListener implements ApplicationListener<SessionConnectEvent> {

    private final ChatService chatService;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        var principal = event.getUser();
        chatService.handleUserJoin(principal.getName());
        log.info("User '{}' connected to chat", principal.getName());
    }
}
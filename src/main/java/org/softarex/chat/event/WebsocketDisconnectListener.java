package org.softarex.chat.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.softarex.chat.service.ChatService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Objects;

import static org.springframework.web.socket.CloseStatus.PROTOCOL_ERROR;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebsocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    private final ChatService chatService;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        boolean notProtocolError = !Objects.equals(PROTOCOL_ERROR.getCode(), event.getCloseStatus().getCode());
        if (notProtocolError) {
            var principal = event.getUser();
            chatService.handleUserLeave(principal.getName());
            log.info("User '{}' disconnected from chat!", principal.getName());
        }
    }
}
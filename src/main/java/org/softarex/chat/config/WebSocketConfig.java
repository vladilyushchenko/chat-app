package org.softarex.chat.config;

import lombok.RequiredArgsConstructor;
import org.softarex.chat.service.JwtProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.List;
import java.util.Map;

import static org.softarex.chat.constants.ChatConstants.*;
import static org.softarex.chat.util.AuthUtil.extractTokenFromHeader;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.messaging.simp.stomp.StompCommand.CONNECT;
import static org.springframework.messaging.support.MessageHeaderAccessor.getAccessor;
import static org.springframework.messaging.support.NativeMessageHeaderAccessor.NATIVE_HEADERS;


@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtProvider jwtProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(TOPIC);
        config.setApplicationDestinationPrefixes(APP);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint(CHAT)
                .setAllowedOriginPatterns("*");
        registry.addEndpoint(CHAT)
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                var accessor = getAccessor(message, StompHeaderAccessor.class);
                if (CONNECT.equals(accessor.getCommand())) {
                    String accessToken = extractTokenFromHeader(getNativeHeadersMap(message)
                            .get(AUTHORIZATION).get(0));
                    var auth = jwtProvider.verifyToken(accessToken);
                    accessor.setUser(auth);
                }
                return message;
            }
        });
    }

    private Map<String, List<String>> getNativeHeadersMap(Message<?> message) {
        return (Map<String, List<String>>) message.getHeaders()
                .get(NATIVE_HEADERS, Map.class);
    }
}
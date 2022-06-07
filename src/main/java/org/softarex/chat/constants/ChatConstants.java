package org.softarex.chat.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ChatConstants {

    public static final String CHAT = "/chat";
    public static final String TOPIC = "/topic";
    public static final String APP = "/app";
    public static final String MESSAGES_TOPIC = TOPIC + "/messages";
    public static final String HANDSHAKE_ENDPOINT = CHAT;
    public static final String TOKEN = "token";
}
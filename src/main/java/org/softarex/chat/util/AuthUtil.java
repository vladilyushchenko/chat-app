package org.softarex.chat.util;

import lombok.experimental.UtilityClass;

import static org.springframework.util.StringUtils.hasText;

@UtilityClass
public class AuthUtil {

    public static String extractTokenFromHeader(String token) {
        if (hasText(token) && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}
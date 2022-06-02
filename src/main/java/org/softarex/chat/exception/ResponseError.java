package org.softarex.chat.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ResponseError {
    private final String msg;
    private final LocalDateTime timestamp;
    private final Integer code;
}
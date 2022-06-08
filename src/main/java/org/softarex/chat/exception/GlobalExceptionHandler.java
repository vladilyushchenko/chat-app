package org.softarex.chat.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static org.softarex.chat.constants.ErrorConstants.SERVER_ERROR_MSG;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleServerException(Exception e) {
        log.warn("INTERNAL SERVER ERROR {}", e.getMessage());
        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(getResponseError(SERVER_ERROR_MSG, INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ResponseError> handleAlreadyAuthenticated(UserAlreadyExistsException e) {
        return ResponseEntity.status(BAD_REQUEST)
                .body(getResponseError(e.getMsg(), BAD_REQUEST));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ResponseError> handleEntityNotFound(EntityNotFoundException e) {
        return new ResponseEntity<>(getResponseError(e.getMsg(), NOT_FOUND), NOT_FOUND);
    }

    private ResponseError getResponseError(String msg, HttpStatus status) {
        return new ResponseError(msg, LocalDateTime.now(), status.value());
    }
}
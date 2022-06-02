package org.softarex.chat.exception;

import static java.lang.String.format;
import static org.softarex.chat.constants.ErrorConstants.USER_ALREADY_IN_CHAT_MSG;

public class UserAlreadyExistsException extends BaseException {

    public UserAlreadyExistsException(String username) {
        super(format(USER_ALREADY_IN_CHAT_MSG, username));
    }
}
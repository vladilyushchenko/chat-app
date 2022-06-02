package org.softarex.chat.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AuthDto {

    @NotNull
    private String username;
}
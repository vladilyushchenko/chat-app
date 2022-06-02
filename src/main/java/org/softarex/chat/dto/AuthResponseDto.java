package org.softarex.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(staticName = "of")
public class AuthResponseDto {
    private String accessToken;
    private String refreshToken;
}
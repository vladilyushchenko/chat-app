package org.softarex.chat.controller;

import lombok.RequiredArgsConstructor;
import org.softarex.chat.dto.AuthDto;
import org.softarex.chat.dto.AuthResponseDto;
import org.softarex.chat.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public AuthResponseDto login(@RequestBody @Validated AuthDto authDto,
                                 HttpServletRequest request) {
        return service.login(authDto, request.getRequestURL().toString());
    }

    @PostMapping("/token/refresh")
    public AuthResponseDto refreshToken(@RequestBody @NotNull String refreshToken,
                                        HttpServletRequest request) {
        return service.refreshToken(refreshToken, request.getRequestURL().toString());
    }

    @PostMapping("/logout")
    public void logout(@RequestBody @Validated AuthDto authDto) {
        service.logout(authDto);
    }
}
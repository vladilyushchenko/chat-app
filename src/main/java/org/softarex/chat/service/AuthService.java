package org.softarex.chat.service;

import lombok.RequiredArgsConstructor;
import org.softarex.chat.dto.AuthDto;
import org.softarex.chat.dto.AuthResponseDto;
import org.softarex.chat.model.User;
import org.springframework.stereotype.Service;

import static org.softarex.chat.util.UserRoleUtil.mapToAuthorities;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    public AuthResponseDto login(AuthDto authDto, String issuer) {
        String username = authDto.getUsername();
        User user = new User();
        user.setUsername(username);
        user = userService.createUser(user);

        String accessToken = jwtProvider.getAccessToken(issuer, user.getUsername(), mapToAuthorities(user.getRoles()));
        String refreshToken = jwtProvider.getRefreshToken(issuer, user.getUsername());
        return AuthResponseDto.of(accessToken, refreshToken);
    }

    public AuthResponseDto refreshToken(String refreshToken, String issuer) {
        String username = jwtProvider.verifyAndGetRefreshTokenUsername(refreshToken);
        User user = userService.getByUserName(username);

        String newAccessToken = jwtProvider.getAccessToken(issuer, username, mapToAuthorities(user.getRoles()));
        String newRefreshToken = jwtProvider.getRefreshToken(issuer, username);
        return AuthResponseDto.of(newAccessToken, newRefreshToken);
    }

    public void logout(AuthDto authDto) {
        userService.deleteByUsername(authDto.getUsername());
    }
}
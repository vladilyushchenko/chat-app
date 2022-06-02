package org.softarex.chat.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toList;
import static org.softarex.chat.constants.AuthConstants.ROLES_CLAIM;

@Component
public class JwtProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    public String getAccessToken(String issuer, String username, List<String> authorities) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(currentTimeMillis() + 30 * 60 * 1000))
                .withClaim(ROLES_CLAIM, authorities)
                .withIssuer(issuer)
                .sign(HMAC256(jwtSecret.getBytes(UTF_8)));
    }

    public String getRefreshToken(String issuer, String username) {
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(currentTimeMillis() + 300 * 60 * 1000))
                .withIssuer(issuer)
                .sign(HMAC256(jwtSecret.getBytes(UTF_8)));
    }

    public Authentication verifyToken(String token) {
        JWTVerifier verifier = JWT.require(HMAC256(jwtSecret.getBytes(UTF_8))).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim(ROLES_CLAIM).asList(String.class);
        List<GrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
        return new UsernamePasswordAuthenticationToken(username,null, authorities);
    }

    public String verifyAndGetRefreshTokenUsername(String token) {
        JWTVerifier verifier = JWT.require(HMAC256(jwtSecret.getBytes(UTF_8))).build();
        DecodedJWT decodedJWT = verifier.verify(token);
        return decodedJWT.getSubject();
    }
}

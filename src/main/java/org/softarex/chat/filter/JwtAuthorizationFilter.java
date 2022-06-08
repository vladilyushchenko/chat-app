package org.softarex.chat.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.softarex.chat.service.JwtProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.util.Objects.nonNull;
import static org.softarex.chat.util.AuthUtil.extractTokenFromHeader;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        String token = extractTokenFromHeader(((HttpServletRequest) request).getHeader(AUTHORIZATION));
        if (nonNull(token)) {
            authenticate(token);
        }
        chain.doFilter(request, response);
    }

    private void authenticate(String token) {
        try {
            var auth = jwtProvider.verifyToken(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (JWTVerificationException e) {
            // authorizing failed
        }
    }
}
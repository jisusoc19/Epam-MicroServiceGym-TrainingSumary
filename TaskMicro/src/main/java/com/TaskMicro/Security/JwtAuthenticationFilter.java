package com.TaskMicro.Security;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.IOException;
import java.util.Optional;

import static org.springframework.cloud.openfeign.security.OAuth2AccessTokenInterceptor.BEARER;


@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTVerifier jwtVerifier;

    public JwtAuthenticationFilter() {
        // Configura el algoritmo y el verificador según tus necesidades
        Algorithm algorithm = Algorithm.HMAC256("secret"); // Usa tu clave secreta aquí
        this.jwtVerifier = JWT.require(algorithm).build();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {
        try {
            extractTokenFromRequest(request)
                    .map(token -> jwtVerifier.verify(token))  // Usa el jwtVerifier para verificar el token
                    .ifPresent(decodedJWT -> SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, null)));
        } catch (Exception e) {
            log.error("Error decoding token: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
            return Optional.of(token.substring(BEARER.length()));
        }
        return Optional.empty();
    }
}
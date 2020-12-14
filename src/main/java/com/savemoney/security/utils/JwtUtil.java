package com.savemoney.security.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.savemoney.security.domain.responses.TokenPayloadResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String payload) {
        return Jwts.builder()
                .setSubject(payload)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS512, secret.getBytes())
                .compact();
    }

    public boolean isTokenValid(String token) {
        boolean isValid = false;
        Claims claims = getClaims(token);
        if (Objects.nonNull(claims)) {
            String username = claims.getSubject();
            Date expirationDate = claims.getExpiration();
            Date now = new Date(System.currentTimeMillis());

            isValid = Objects.nonNull(username) && isTokenNotExpiredTime(expirationDate, now);
        }

        return isValid;
    }

    private Claims getClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isTokenNotExpiredTime(Date expirationDate, Date now) {
        return Objects.nonNull(expirationDate) && now.before(expirationDate);
    }

    public TokenPayloadResponse getPayloadFromToken(String token) {
        Claims claims = getClaims(token.substring(7));
        String payload = Objects.nonNull(claims) ? claims.getSubject() : null;
        try {
            return new ObjectMapper().readValue(payload, TokenPayloadResponse.class);
        } catch (JsonProcessingException e) {
            return TokenPayloadResponse.builder().build();
        }
    }

}

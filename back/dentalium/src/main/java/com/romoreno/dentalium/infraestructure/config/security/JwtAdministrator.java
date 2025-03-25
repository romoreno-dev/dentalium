package com.romoreno.dentalium.infraestructure.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
public class JwtAdministrator {

    @Value("${jwt.secret}")
    private String secret;
    private SecretKey secretKey;

    private final long TTL_MILLIS = 15 * 60 * 1000L;

    public String generateJwt(String username, String id, List<String> roles) {
        final var keyBytes = Base64.getDecoder().decode(secret);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);

        var expirationTime = new Date(System.currentTimeMillis() + TTL_MILLIS);

        return Jwts.builder()
                .subject(username)
                .claim("id", id)
                .claim("roles", roles)
                .issuedAt(new Date())
                .expiration(expirationTime)
                .signWith(secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromJwt(String token) {
        try {
            return getClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getIdFromJwt(String token) {
        try {
            final var claims = getClaims(token);
            return claims.get("id", String.class);
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> getRolesFromJwt(String token) {
        try {
            final var claims = getClaims(token);
            return claims.get("roles", List.class);
        } catch (Exception e) {
            return null;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
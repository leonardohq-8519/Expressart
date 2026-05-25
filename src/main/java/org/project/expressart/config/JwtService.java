package org.project.expressart.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.project.expressart.Usuario.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }

    public String generateToken(Usuario user) {
        long now = System.currentTimeMillis();
        long expiration = now + 1000 * 60 * 60;

        return Jwts.builder()
                .setSubject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("token_version", user.getToken())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    public Long extractUserId(String token){
        return Long.parseLong(extractClaims(token).getSubject());
    }

    public Integer extractTokenVersion(String token) {
        return extractClaims(token).get("token_version", Integer.class);
    }

    public boolean isTokenValid(String token, Integer actualTokenVersion) {
        try {
            Claims claims = extractClaims(token);
            boolean notExpired = claims.getExpiration().after(new Date());
            boolean validVersion = extractTokenVersion(token).equals(actualTokenVersion);
            return notExpired && validVersion;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
package com.impacto.infrastructure.security;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.*;
import java.util.*;
@Service
public class JwtService {
    private final SecretKey key;
    private final long expirationSeconds;
    public JwtService(@Value("${security.jwt.secret}") String secret,@Value("${security.jwt.expiration-seconds}") long exp) {
        if(secret.length()<32) throw new IllegalArgumentException("JWT secret debe tener al menos 32 caracteres");
        key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        expirationSeconds=exp;
    }
    public String generate(UUID id,String email,String role) {
        Instant now=Instant.now();
        return Jwts.builder().subject(email).claim("uid",id.toString()).claim("role",role).issuedAt(Date.from(now)).expiration(Date.from(now
            .plusSeconds(expirationSeconds))).signWith(key).compact();
    }
    public Claims parse(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }
    public Instant expiry(String token) {
        return parse(token).getExpiration().toInstant();
    }
}

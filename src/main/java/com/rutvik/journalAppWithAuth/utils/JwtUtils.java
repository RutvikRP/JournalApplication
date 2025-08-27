package com.rutvik.journalAppWithAuth.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    @Value("${jwt.expiration}")
    private long EXPIRATION_MS;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder().setSubject(username).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS)).signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    public String extractUsername(String jwtToken) {
        Claims claims = extractClaims(jwtToken);
        return claims.getSubject();
    }

    public Claims extractClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    public Date extractExpDate(String jwtToken) {
        Claims claims = extractClaims(jwtToken);
        return claims.getExpiration();
    }

    public boolean isValidToken(String jwtToken, String username) {
        return extractExpDate(jwtToken).after(new Date()) && extractUsername(jwtToken).equals(username);
    }
}

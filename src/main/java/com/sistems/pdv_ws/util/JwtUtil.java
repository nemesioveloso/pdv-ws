package com.sistems.pdv_ws.util;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "secret_key"; // Substitua por uma chave segura.

    public String gerarToken(String email, String role, Long lojaId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        claims.put("lojaId", lojaId);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 dia
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    public boolean isTokenValido(String token) {
        try {
            validarToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

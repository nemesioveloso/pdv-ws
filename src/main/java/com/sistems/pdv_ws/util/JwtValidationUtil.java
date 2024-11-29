package com.sistems.pdv_ws.util;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtValidationUtil {

    @Autowired
    private JwtUtil jwtUtil;

    public Claims validarToken(String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("Token ausente ou inválido");
        }
        return jwtUtil.validarToken(token.substring(7)); // Remove "Bearer "
    }

    public boolean isAdminOrFuncionario(Claims claims) {
        String role = claims.get("role", String.class);
        return "ADMIN".equals(role) || "FUNCIONARIO".equals(role);
    }
}

package com.sistems.pdv_ws.security;

import com.sistems.pdv_ws.util.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            if (jwtUtil.isTokenValido(token)) {
                Claims claims = jwtUtil.validarToken(token);
                String role = claims.get("role", String.class);
                Long lojaId = claims.get("lojaId", Long.class);

                // Adicione lógica de validação de permissões aqui, se necessário.
                // Por exemplo, você pode configurar o contexto de segurança.
            }
        }
        filterChain.doFilter(request, response);
    }
}

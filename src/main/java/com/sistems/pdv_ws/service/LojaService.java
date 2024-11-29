package com.sistems.pdv_ws.service;

import com.sistems.pdv_ws.model.Loja;
import com.sistems.pdv_ws.repository.LojaRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LojaService {

    private static final String SECRET_KEY = "secret_key"; // Substitua por uma chave segura.

    @Autowired
    private LojaRepository lojaRepository;

    public Loja salvarLoja(Loja loja) {
        return lojaRepository.save(loja);
    }

    public Optional<Loja> buscarPorId(Long id) {
        return lojaRepository.findById(id);
    }

    public Optional<Loja> buscarPorEmail(String email) {
        return lojaRepository.findByEmail(email);
    }

    public String gerarToken(Loja loja) {
        return Jwts.builder()
                .setSubject(loja.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token válido por 1 dia.
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validarToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

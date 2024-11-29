package com.sistems.pdv_ws.controller;

import com.sistems.pdv_ws.model.Loja;
import com.sistems.pdv_ws.service.LojaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private LojaService lojaService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Loja> registrarLoja(@RequestBody Loja loja) {
        loja.setSenha(passwordEncoder.encode(loja.getSenha()));
        return ResponseEntity.ok(lojaService.salvarLoja(loja));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Loja loja) {
        Loja lojaExistente = lojaService.buscarPorEmail(loja.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Loja não encontrada."));
        if (passwordEncoder.matches(loja.getSenha(), lojaExistente.getSenha())) {
            String token = lojaService.gerarToken(lojaExistente);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        }
    }
}

package com.sistems.pdv_ws.controller;

import com.sistems.pdv_ws.model.Loja;
import com.sistems.pdv_ws.model.Produto;
import com.sistems.pdv_ws.service.LojaService;
import com.sistems.pdv_ws.service.ProdutoService;
import com.sistems.pdv_ws.util.JwtValidationUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private JwtValidationUtil jwtValidationUtil;

    @Autowired
    private LojaService lojaService;

    @PostMapping
    public ResponseEntity<Produto> criarProduto(@RequestBody Produto produto, @RequestHeader("Authorization") String token) {
        Claims claims = jwtValidationUtil.validarToken(token);
        if (!jwtValidationUtil.isAdminOrFuncionario(claims)) {
            return ResponseEntity.status(403).build();
        }

        Long lojaId = claims.get("lojaId", Long.class);
        Loja loja = lojaService.buscarPorId(lojaId).orElseThrow(() -> new RuntimeException("Loja não encontrada"));
        produto.setLoja(loja); // Vincular o produto à loja

        return ResponseEntity.ok(produtoService.salvarProduto(produto));
    }

    @GetMapping
    public ResponseEntity<List<Produto>> listarProdutos(@RequestHeader("Authorization") String token) {
        Claims claims = jwtValidationUtil.validarToken(token);
        if (!jwtValidationUtil.isAdminOrFuncionario(claims)) {
            return ResponseEntity.status(403).build(); // Sem permissão
        }

        Long lojaId = claims.get("lojaId", Long.class);
        return ResponseEntity.ok(produtoService.listarProdutos(lojaId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id, @RequestBody Produto produto, @RequestHeader("Authorization") String token) {
        Claims claims = jwtValidationUtil.validarToken(token);
        if (!jwtValidationUtil.isAdminOrFuncionario(claims)) {
            return ResponseEntity.status(403).build(); // Sem permissão
        }

        Long lojaId = claims.get("lojaId", Long.class);
        Produto existente = produtoService.buscarPorId(id)
                .filter(p -> p.getLoja().getId().equals(lojaId)) // Verificar se a loja vinculada ao produto corresponde à do token
                .orElseThrow(() -> new RuntimeException("Produto não encontrado ou não pertence à loja"));

        produto.setId(id);
        produto.setLoja(existente.getLoja()); // Manter a loja vinculada ao produto

        return ResponseEntity.ok(produtoService.atualizarProduto(produto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirProduto(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        Claims claims = jwtValidationUtil.validarToken(token);
        if (!jwtValidationUtil.isAdminOrFuncionario(claims)) {
            return ResponseEntity.status(403).build();
        }

        Long lojaId = claims.get("lojaId", Long.class);
        Produto produto = produtoService.buscarPorId(id)
                .filter(p -> p.getLoja().getId().equals(lojaId))
                .orElseThrow(() -> new RuntimeException("Produto não encontrado ou não pertence à loja"));

        produtoService.excluirProduto(produto.getId());
        return ResponseEntity.noContent().build();
    }
}

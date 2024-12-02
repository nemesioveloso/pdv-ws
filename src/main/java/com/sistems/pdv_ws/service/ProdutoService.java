package com.sistems.pdv_ws.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistems.pdv_ws.model.Produto;
import com.sistems.pdv_ws.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    private static final Logger logger = LoggerFactory.getLogger(ProdutoService.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private AuditoriaService auditoriaService;

    @Autowired
    private ObjectMapper objectMapper;

    public Produto salvarProduto(Produto produto, String usuario) {
        if (produto.getEstoqueAtual() < produto.getEstoqueMinimo()) {
            throw new IllegalStateException("Estoque baixo! Reabasteça o produto: " + produto.getNome());
        }
        Produto savedProduto = produtoRepository.save(produto);
        logger.info("Usuário '{}' adicionou o produto: {}", usuario, produto.getNome());

        // Registrar auditoria
        try {
            String payload = objectMapper.writeValueAsString(produto);
            auditoriaService.registrar("Produto", "ADICIONADO", usuario, payload);
        } catch (Exception e) {
            logger.error("Erro ao registrar auditoria para adicionar produto: {}", e.getMessage());
        }

        return savedProduto;
    }

    public List<Produto> listarProdutos(Long lojaId) {
        return produtoRepository.findByLoja_Id(lojaId);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void excluirProduto(Long id, String usuario) {
        Optional<Produto> produtoOpt = produtoRepository.findById(id);
        if (produtoOpt.isPresent()) {
            Produto produto = produtoOpt.get();
            produtoRepository.deleteById(id);
            logger.info("Usuário '{}' excluiu o produto: {}", usuario, produto.getNome());

            // Registrar auditoria
            try {
                String payload = objectMapper.writeValueAsString(produto);
                auditoriaService.registrar("Produto", "EXCLUIDO", usuario, payload);
            } catch (Exception e) {
                logger.error("Erro ao registrar auditoria para excluir produto: {}", e.getMessage());
            }
        } else {
            throw new RuntimeException("Produto não encontrado");
        }
    }

    public Produto atualizarProduto(Produto produto, String usuario) {
        if (produto.getId() == null) {
            throw new IllegalArgumentException("O ID do produto é obrigatório para atualização.");
        }
        Produto updatedProduto = produtoRepository.save(produto);
        logger.info("Usuário '{}' atualizou o produto: {}", usuario, produto.getNome());

        // Registrar auditoria
        try {
            String payload = objectMapper.writeValueAsString(produto);
            auditoriaService.registrar("Produto", "EDITADO", usuario, payload);
        } catch (Exception e) {
            logger.error("Erro ao registrar auditoria para atualizar produto: {}", e.getMessage());
        }

        return updatedProduto;
    }

    public Double calcularLucroTotal() {
        return produtoRepository.findAll()
                .stream()
                .mapToDouble(produto ->
                        (produto.getValorVenda() - produto.getValorCompra()) * produto.getEstoqueAtual()
                ).sum();
    }


}

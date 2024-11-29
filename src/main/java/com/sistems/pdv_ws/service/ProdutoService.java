package com.sistems.pdv_ws.service;

import com.sistems.pdv_ws.model.Produto;
import com.sistems.pdv_ws.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    public Produto salvarProduto(Produto produto) {
        if (produto.getEstoqueAtual() < produto.getEstoqueMinimo()) {
            throw new IllegalStateException("Estoque baixo! Reabasteça o produto: " + produto.getNome());
        }
        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos(Long lojaId) {
        return produtoRepository.findByLoja_Id(lojaId);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return produtoRepository.findById(id);
    }

    public void excluirProduto(Long id) {
        produtoRepository.deleteById(id);
    }

    public Produto atualizarProduto(Produto produto) {
        if (produto.getId() == null) {
            throw new IllegalArgumentException("O ID do produto é obrigatório para atualização.");
        }
        return produtoRepository.save(produto);
    }

    public Double calcularLucroTotal() {
        return produtoRepository.findAll()
                .stream()
                .mapToDouble(produto ->
                        (produto.getValorVenda() - produto.getValorCompra()) * produto.getEstoqueAtual()
                ).sum();
    }


}

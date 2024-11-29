package com.sistems.pdv_ws.service;

import com.sistems.pdv_ws.model.Categoria;
import com.sistems.pdv_ws.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Categoria salvarCategoria(Categoria categoria) {
        return categoriaRepository.save(categoria);
    }

    public List<Categoria> listarCategorias() {
        return categoriaRepository.findAll();
    }

    public Optional<Categoria> buscarPorId(Long id) {
        return categoriaRepository.findById(id);
    }

    public void excluirCategoria(Long id) {
        categoriaRepository.deleteById(id);
    }

    public Categoria atualizarCategoria(Categoria categoria) {
        if (categoria.getId() == null) {
            throw new IllegalArgumentException("O ID da categoria é obrigatório para atualização.");
        }
        return categoriaRepository.save(categoria);
    }
}

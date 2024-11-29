package com.sistems.pdv_ws.repository;

import com.sistems.pdv_ws.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    List<Produto> findByLoja_Id(Long lojaId);
}

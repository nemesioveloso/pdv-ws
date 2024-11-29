package com.sistems.pdv_ws.repository;

import com.sistems.pdv_ws.model.Loja;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LojaRepository extends JpaRepository<Loja, Long> {
    Optional<Loja> findByEmail(String email);
}

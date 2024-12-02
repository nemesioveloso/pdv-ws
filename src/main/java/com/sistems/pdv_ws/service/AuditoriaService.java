package com.sistems.pdv_ws.service;

import com.sistems.pdv_ws.model.Auditoria;
import com.sistems.pdv_ws.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public void registrar(String entidade, String acao, String usuario, String payload) {
        Auditoria auditoria = Auditoria.builder()
                .entidade(entidade)
                .acao(acao)
                .usuario(usuario)
                .dataHora(LocalDateTime.now())
                .payload(payload)
                .build();
        auditoriaRepository.save(auditoria);
    }
}

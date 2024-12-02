package com.sistems.pdv_ws.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "auditorias")
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String entidade;

    @Column(nullable = false)
    private String acao;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Lob
    @Column(nullable = false)
    private String payload;
}

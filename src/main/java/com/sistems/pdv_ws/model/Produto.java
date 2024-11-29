package com.sistems.pdv_ws.model;
import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "produtos")
public class Produto {

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "loja_id", nullable = false)
    private Loja loja;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "O nome do produto é obrigatório.")
    private String nome;

    @Column(nullable = false)
    @NotNull(message = "O valor de compra é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor de compra deve ser maior que zero.")
    private Double valorCompra;

    @Column(nullable = false)
    @NotNull(message = "O valor de venda é obrigatório.")
    @DecimalMin(value = "0.0", inclusive = false, message = "O valor de venda deve ser maior que zero.")
    private Double valorVenda;

    @Column(nullable = false)
    @NotNull(message = "O estoque atual é obrigatório.")
    @Min(value = 0, message = "O estoque atual não pode ser negativo.")
    private Integer estoqueAtual;

    @Column(nullable = false)
    @NotNull(message = "O estoque mínimo é obrigatório.")
    @Min(value = 0, message = "O estoque mínimo não pode ser negativo.")
    private Integer estoqueMinimo;
}

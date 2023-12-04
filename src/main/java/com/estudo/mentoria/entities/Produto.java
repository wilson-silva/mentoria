package com.estudo.mentoria.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID idproduto;
    @Column(nullable = false, unique = true)
    private String nome;
    private String descricao;
    private Double preco;
    private Integer estoque;
    private Boolean estado = true;
    private String fabricante;
    @ManyToOne//(fetch = FetchType.EAGER)
    private Categoria categoria;


}

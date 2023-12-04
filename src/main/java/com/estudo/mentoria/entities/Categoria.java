package com.estudo.mentoria.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_categoria")
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String titulo;
    private Boolean estado = true;
    @JsonIgnore
    @OneToMany(mappedBy="categoria", fetch = FetchType.EAGER)
    private List<Produto> produtos = new ArrayList<>();
}

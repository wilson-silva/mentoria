package com.estudo.mentoria.services;

import com.estudo.mentoria.entities.Categoria;
import com.estudo.mentoria.entities.Produto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@WebAppConfiguration
class ProdutoServiceTest {
    @Autowired
    private ProdutoService produtoService;

    @Test
    @DisplayName("Deve somar os preços dos produtos de uma lista")
    public void ProdutoServiceSomaPrecoProduto(){

        var categoriaA = Categoria.builder()
                .id(UUID.fromString("afa52542-87ee-11ee-b9d1-0242ac120002"))
                .titulo("eletronicos")
                .estado(true).build();

        var produto1 = Produto.builder()
                .idproduto(UUID.fromString("02c6323e-87ef-11ee-b9d1-0242ac120002"))
                .nome("produtoA")
                .preco(100.00)
                .categoria(categoriaA)
                .build();

        var produto2 = Produto.builder()
                .idproduto(UUID.fromString("02c6323e-87ef-11ee-b9d1-0242ac120007"))
                .nome("produtoA")
                .preco(100.00)
                .categoria(categoriaA)
                .build();

        List<Produto> lista = List.of(produto1, produto2);

        double resultado = produtoService.somarPrecos(lista);

        assertEquals(200.0, resultado, 0.001);

    }


}
package com.estudo.mentoria.controller;

import com.estudo.mentoria.mapper.ProdutoMapper;
import com.estudo.mentoria.dto.produto.ProdutoRequestDto;
import com.estudo.mentoria.dto.produto.ProdutoResponseDto;
import com.estudo.mentoria.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/produtos")
@Tag(name = "mentoria")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ProdutoMapper mapper;

    Logger logger = LogManager.getLogger(ProdutoController.class);

    public ProdutoController(ProdutoService produtoService, ProdutoMapper mapper) {
        this.produtoService = produtoService;
        this.mapper = mapper;
    }

    @Operation(summary = "Lista todos os produtos", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<ProdutoResponseDto>> listarTodosProdutos(Pageable pageable) {
        logger.trace("TRACE");
        logger.debug("DEBUG");
        logger.info("INFO");
        logger.warn("WARN");
        logger.error("ERROR");
        logger.fatal("FATAL");
        return ResponseEntity.ok(produtoService.findAll(pageable).map(mapper::toProdutoResponse));
    }

    @Operation(summary = "Lista produtos por critérios", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca")
    })
    @GetMapping("/buscar")
    public ResponseEntity<Page<ProdutoResponseDto>> listarTodosProdutosComCriterio(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String descricao,
            @RequestParam(required = false) String categoriaTitulo,
            @RequestParam(required = false) String fabricante,
            Pageable pageable) {
        return ResponseEntity.ok(produtoService.filtraProdutosPorCriterios(nome, descricao, categoriaTitulo, fabricante, pageable).map(mapper::toProdutoResponse));
    }

    @Operation(summary = "Busca um produto por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado ou excluído"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDto> buscarProduto(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toProdutoResponse(produtoService.findById(id)));
    }

    @Operation(summary = "Cadastra um produto", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar cadastro")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> cadastrarProduto(@RequestBody @Valid ProdutoRequestDto request) {
        var produto = mapper.toProduto(request);
        produtoService.save(produto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso");
    }

    @Operation(summary = "Altera um produto por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto alterado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado ou excluído"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar alteração")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> alterarProduto(@PathVariable("id") UUID id,
                                                 @RequestBody @Valid ProdutoRequestDto request) {
        var produto = mapper.toProduto(request);
        var savedProduto = produtoService.update(id, produto);
        mapper.toProdutoResponse(savedProduto);
        return ResponseEntity.ok("Dados atualizados com sucesso!");
    }

    @Operation(summary = "Deleta um produto por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado ou excluído"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar deleção")
    })
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> excluirProduto(@PathVariable UUID id) {
        produtoService.delete(id);
        return ResponseEntity.ok("Produto excluído (estado atualizado).");
    }
}



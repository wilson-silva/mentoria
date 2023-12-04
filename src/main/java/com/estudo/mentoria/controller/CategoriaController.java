package com.estudo.mentoria.controller;

import com.estudo.mentoria.dto.categoria.CategoriaRequestDto;
import com.estudo.mentoria.dto.categoria.CategoriaResponseDto;
import com.estudo.mentoria.entities.Categoria;
import com.estudo.mentoria.mapper.CategoriaMapper;
import com.estudo.mentoria.services.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService categoriaService;
    private final CategoriaMapper categoriaMapper;

    public CategoriaController(CategoriaService categoriaService, CategoriaMapper categoriaMapper) {
        this.categoriaService = categoriaService;
        this.categoriaMapper = categoriaMapper;
    }

    @Operation(summary = "Lista todos as categorias", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca dos dados")
    })
    @GetMapping
    public ResponseEntity<Page<CategoriaResponseDto>> listarTodasCategorias(Pageable pageable){
        return ResponseEntity.ok(categoriaService.findAll(pageable).map(categoriaMapper::toCategoriaResponse));
    }

    @Operation(summary = "Busca uma categoria por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "402", description = "Categoria não encontrado ou excluída"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> buscarCategoria(@PathVariable UUID id){
        Categoria categoria = categoriaService.findById(id);
        return ResponseEntity.ok(categoriaMapper.toCategoriaResponse(categoria));
    }

    @Operation(summary = "Cadastra uma categoria", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria cadastrada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar busca")
    })
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> cadastrarCategoria(@RequestBody @Valid CategoriaRequestDto categoriaRequestDto){
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequestDto);
        categoriaService.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body("Cadastro realizado com sucesso");
    }

    @Operation(summary = "Altera uma categoria por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria alterada com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada ou excluída"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar alteração")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> alterarCategoria(@PathVariable("id") UUID id, @RequestBody @Valid CategoriaRequestDto categoriaRequestDto){
        Categoria categoria = categoriaMapper.toCategoria(categoriaRequestDto);
        Categoria categoriaSalva = categoriaService.update(id, categoria);
        categoriaMapper.toCategoriaResponse(categoriaSalva);
        return ResponseEntity.ok("Dados atualizados com sucesso!");
    }


    @Operation(summary = "Deleta uma categoria por ID", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada ou excluída"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar alteração")
    })
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> excluirCategoria(@PathVariable UUID id) {
        categoriaService.delete(id);
        return ResponseEntity.ok("Categoria excluída (estado atualizado).");
    }
}
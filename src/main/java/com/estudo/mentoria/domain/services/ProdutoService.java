package com.estudo.mentoria.domain.services;

import com.estudo.mentoria.domain.entities.Produto;
import com.estudo.mentoria.domain.repositories.ProdutoRepository;
import com.estudo.mentoria.exception.BadRequestException;
import com.estudo.mentoria.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class ProdutoService implements IService<Produto> {

    private final ProdutoRepository repository;
    private final CategoriaService categoriaService;

    @Override
    public Page<Produto> findAll(Pageable pageable) {
        return repository.buscarProdutoEstadoTrue(pageable);
    }

    public Page<Produto> filtraProdutosPorCriterios(String nome, String descricao, String categoria, String fabricante, Pageable pageable) {
        return repository.findByNomeContainingOrDescricaoContainingOrCategoriaTituloContainingOrFabricanteContaining(nome, descricao, categoria, fabricante, pageable);
    }

    @Override
    public Produto findById(UUID id) {
        return repository.findById(id)
                .filter(Produto::getEstado)
                .orElseThrow(() -> new NotFoundException("Produto não encontrado ou excluído!"));
    }

    @Override
    public void save(Produto produto) {
        if (existeNome(produto.getNome())) {
            throw new BadRequestException("Produto já existe");
        }
        var categoria = categoriaService.findById(produto.getCategoria().getId());
        if (!categoriaService.existeId(categoria.getId())) {
            throw new BadRequestException("Categoria não encontrada!");
        }
        produto.setCategoria(categoria);
        repository.save(produto);
    }

    @Override
    public Produto update(UUID id, Produto produto) {
        this.findById(id);
        produto.setIdproduto(id);
        return repository.save(produto);
    }

    public boolean existeNome(String nome) {
        return repository.existsByNome(nome);
    }

    @Override
    public void delete(UUID id) {
        Produto produto = findById(id);
        produto.setEstado(false);
        repository.save(produto);
    }
}






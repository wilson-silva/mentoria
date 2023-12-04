package com.estudo.mentoria.services;

import com.estudo.mentoria.entities.Categoria;
import com.estudo.mentoria.exception.BadRequestException;
import com.estudo.mentoria.exception.NotFoundException;
import com.estudo.mentoria.repositories.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class CategoriaService implements IService<Categoria> {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Page<Categoria> findAll(Pageable pageable) {
        return repository.buscarCategoriaEstadoTrue(pageable);
    }

    @Override
    public Categoria findById(UUID id) {
        return repository.findById(id)
                .filter(Categoria::getEstado)
                .orElseThrow(() -> new NotFoundException("Categoria não encontrada ou excluída!"));
    }

    @Override
    public void save(Categoria categoria) {
        if (existeTitulo(categoria.getTitulo())) {
            throw new BadRequestException("Categoria já existe");
        }
        repository.save(categoria);
    }

    public boolean existeTitulo(String titulo) {
        return repository.existsByTitulo(titulo);
    }

    public boolean existeId(UUID id) {
        return repository.existsById(id);
    }

    @Override
    public Categoria update(UUID id, Categoria categoria) {
        findById(id);
        categoria.setId(id);
        return repository.save(categoria);
    }

    @Override
    public void delete(UUID id) {
        Categoria categoria = findById(id);
        categoria.setEstado(false);
        repository.save(categoria);
    }
}






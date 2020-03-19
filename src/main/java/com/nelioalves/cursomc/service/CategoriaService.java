package com.nelioalves.cursomc.service;

import java.util.Optional;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public Categoria findById(Integer id) {
        Optional<Categoria> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
}
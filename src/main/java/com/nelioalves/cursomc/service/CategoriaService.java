package com.nelioalves.cursomc.service;

import java.util.List;
import java.util.Optional;

import com.nelioalves.cursomc.domain.Categoria;
import com.nelioalves.cursomc.dto.CategoriaDTO;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> findAll() {
        return repo.findAll();
    }

    public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public Categoria findById(Integer id) {
        Optional<Categoria> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
    public Categoria add(Categoria cat) {
        cat.setId(null);
        return repo.save(cat);
    }

    public Categoria update(Categoria cat) {
        Categoria newObj = this.findById(cat.getId());
        newObj.setNome(cat.getNome());
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        this.findById(id);
        repo.deleteById(id);
    }

    public Categoria fromDTO(CategoriaDTO dto) {
        return new Categoria(dto.getId(), dto.getNome());
    }
}
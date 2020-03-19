package com.nelioalves.cursomc.service;

import java.util.Optional;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.ClienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

    public Cliente findById(Integer id) {
        Optional<Cliente> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
}
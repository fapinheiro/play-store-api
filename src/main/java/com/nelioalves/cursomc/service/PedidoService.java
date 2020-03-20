package com.nelioalves.cursomc.service;

import java.util.Optional;

import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repo;

    public Pedido findById(Integer id) {
        Optional<Pedido> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
}
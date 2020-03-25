package com.nelioalves.cursomc.service;

import java.util.List;
import java.util.Optional;

import com.nelioalves.cursomc.domain.Cidade;
import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.Endereco;
import com.nelioalves.cursomc.dto.ClienteDTO;
import com.nelioalves.cursomc.dto.ClienteNewDTO;
import com.nelioalves.cursomc.enums.TipoCliente;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.ClienteRepository;
import com.nelioalves.cursomc.repository.EnderecoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repo;

	@Autowired
    private EnderecoRepository enderecoRepository;
    
    @Autowired
    private BCryptPasswordEncoder pe;
    
    public Cliente findById(Integer id) {
        Optional<Cliente> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente add(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
    }
    
    public Cliente update(Cliente cliente) {
        Cliente newObj = this.findById(cliente.getId());
        newObj.setEmail(cliente.getEmail());
        newObj.setNome(cliente.getNome());
        return repo.save(newObj);
    }

    public void delete(Integer id) {
        this.findById(id);
        repo.deleteById(id);
    }
    
    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }
    
    public Cliente fromDTO(ClienteDTO dto) {
        return new Cliente(dto.getId(), dto.getNome(), dto.getEmail(), null, null, null);
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3()!=null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}
}
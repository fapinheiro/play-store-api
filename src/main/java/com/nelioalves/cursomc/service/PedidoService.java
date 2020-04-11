package com.nelioalves.cursomc.service;

import java.util.Date;
import java.util.Optional;

import com.nelioalves.cursomc.domain.Cliente;
import com.nelioalves.cursomc.domain.ItemPedido;
import com.nelioalves.cursomc.domain.PagamentoBoleto;
import com.nelioalves.cursomc.domain.Pedido;
import com.nelioalves.cursomc.enums.EstadoPagamento;
import com.nelioalves.cursomc.exception.AuthorizationException;
import com.nelioalves.cursomc.exception.NotFoundException;
import com.nelioalves.cursomc.repository.ItemPedidoRepository;
import com.nelioalves.cursomc.repository.PagamentoRepository;
import com.nelioalves.cursomc.repository.PedidoRepository;
import com.nelioalves.cursomc.repository.ProdutoRepository;
import com.nelioalves.cursomc.security.UserSS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
    private ItemPedidoRepository itemPedidoRepository;
    
    @Autowired
	private PedidoRepository repo;
	
	@Autowired
    private ClienteService clientService;

    public Pedido findById(Integer id) {
        Optional<Pedido> cat = repo.findById(id);
        return cat.orElseThrow(() -> 
            new NotFoundException(String.format("Objeto nao encontrado id {%d}", id)));
    }
    
    public Pedido add(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
            final Double preco = produtoRepository.findById(ip.getProduto().getId())
                .orElseThrow( () -> new NotFoundException("ItemProduto nao encontrado {id}" + ip.getProduto().getId()))
                .getPreco();
            ip.setPreco(preco);
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente =  clientService.findById(user.getId());
		return repo.findByCliente(cliente, pageRequest);
	}
}
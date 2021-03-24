package br.com.algaecommerce.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.service.CadastroProdutoService;
import br.com.algaecommerce.domain.repository.PedidoRepository;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	@PersistenceContext EntityManager m;
	
	@Autowired 
	CadastroProdutoService cadastroProduto;
	
	@Autowired 
	PedidoRepository pedidoRepositorio;
	
	
	@RequestMapping(value = "/pedidos", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Pedido cadastrarUmNovoPedido(@RequestBody Pedido p) {
		p.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		p.setId(null);
		m.persist(p);
		return p;
	}
	
	@GetMapping
	public @ResponseBody List<Pedido> buscar() {
		return pedidoRepositorio.findAll();
	}
	
	
}

package br.com.algaecommerce.api.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.repository.PedidoRepository;
import br.com.algaecommerce.domain.repository.ProdutoRepository;

@RestController
@RequestMapping(value = "/pedidos")
public class HomeController {
	@PersistenceContext EntityManager m;
	@Autowired 
	ProdutoRepository produtoRepositorio;
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
	
	@RequestMapping(value = "/produto", method = RequestMethod.POST) @Transactional
	public @ResponseBody Produto cadastrarUmProdutoNovo(@RequestBody Produto novoProdutoCadastro) {
		novoProdutoCadastro.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		novoProdutoCadastro.setId(null);
		m.persist(novoProdutoCadastro);
		return novoProdutoCadastro;
	}
	
	//TODO Está lento em produção, local funciona.
	@GetMapping("/produtos")
	public @ResponseBody List<Produto> buscarProduto() {
		return produtoRepositorio.findAll();
	}
	
	
	@PutMapping("produtos/{produtoId}")
	public ResponseEntity<?>  edita(@PathVariable Long produtoId, @RequestBody Produto pAntigo) {
		try {
			Produto pNoBanco = produtoRepositorio
					.findById(produtoId).orElse(null);

			if (produtoId != null) {
				BeanUtils.copyProperties(pAntigo, pNoBanco,
						"id","dataCriacao", "tags");
				pNoBanco = produtoRepositorio.save(pNoBanco);
				return ResponseEntity.ok(pNoBanco);
			}

			return ResponseEntity.notFound().build();

		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
}

package br.com.algaecommerce.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.repository.PedidoRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/pedidos")
public class HomeController {
	@PersistenceContext EntityManager m;
	@Autowired produtoRepository produtoRepository;
	@Autowired PedidoRepository pedidoRepositorio;
	
	@RequestMapping(value = "/pedidos", method = RequestMethod.POST)
	@Transactional
	public @ResponseBody Pedido cadastrarUmNovoPedido(@RequestBody Pedido p) {
		p.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		p.setId(null);
		m.persist(p);
		return p;
	}
	//TODO Da 200 mas fica mostrando erro
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
	@RequestMapping(value = "/produtos", method = RequestMethod.GET)
	@Transactional
	public @ResponseBody List<Produto> cadastrarUmProdutoNovo() {
		return new produtoRepository(m).findAll();
	}
	
	//TODO da um erro
	@PostMapping("/produtos/{id}")
	public @ResponseBody Produto edita(@PathVariable Long produtoId, @RequestBody Produto pAntigo) {
		pAntigo.setId(produtoId);
		Produto pNoBanco = m.find(Produto.class, pAntigo.getId());
		if (pNoBanco == null) {
			throw new RuntimeException("Erro 404 - Produto não encontrado");
		}
		pNoBanco.setNome(pAntigo.getNome());
		m.persist(pNoBanco);
		return pAntigo;
	}
	
	@Component
	class produtoRepository {
		
		EntityManager manager;
		
		produtoRepository(EntityManager manager) {
			this.manager = manager;
		}
		
		List<Produto> findAll() {
			TypedQuery<Produto> typedQuery = manager.createQuery("select p from Produto p", Produto.class);
			List<Produto> lista = typedQuery.getResultList();
			return new ArrayList<>(lista);
		}
	}
}
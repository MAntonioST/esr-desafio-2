package br.com.algaecommerce.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.algaecommerce.domain.dto.ProdutoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.model.service.CadastroProdutoService;



@RestController
@RequestMapping(value = "/produtos")
public class ProdutoController {

	@Autowired
	CadastroProdutoService cadastroProduto;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto cadastrarUmProdutoNovo(@RequestBody Produto novoProdutoCadastro) {
		return cadastroProduto.salvar(novoProdutoCadastro);
	}

	@GetMapping("/{produtoId}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long produtoId) {
		try {
			ProdutoDTO dto = cadastroProduto.buscarPorId(produtoId);
				return ResponseEntity.ok(dto);
		} catch (EntidadeNaoEncontradaException e) {
			 return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());
		}
		
	}

	@GetMapping
	public ResponseEntity<List<ProdutoDTO>> findAll(){
		List<ProdutoDTO> list = cadastroProduto.listar();
		return ResponseEntity.ok().body(list);
		
	}

	@PutMapping("/{produtoId}")
	public ResponseEntity<?> editar(@PathVariable Long produtoId, @RequestBody Produto pAntigo) {	
		try {
			Produto produtoAtualizado = cadastroProduto.atualizar(produtoId, pAntigo);		
				return ResponseEntity.ok(produtoAtualizado);		
		} catch (EntidadeNaoEncontradaException e) {
			 return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());
		}
	}

	@DeleteMapping("/{produtoId}")
	public ResponseEntity<?> excluir(@PathVariable Long produtoId) {
		try {
			cadastroProduto.excluir(produtoId);
			return ResponseEntity.noContent().build();

		} catch (EntidadeNaoEncontradaException e) {
			 return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());

		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
	}

}

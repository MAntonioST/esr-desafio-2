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

import br.com.algaecommerce.domain.dto.PedidoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.service.CadastroPedidoService;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	
	@Autowired 
	CadastroPedidoService cadastroPedido;
	
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Pedido cadastrarUmNovoPedido(@RequestBody Pedido novoPedido) {
		return cadastroPedido.salvar(novoPedido);
	}
	
	@GetMapping
	public ResponseEntity<List<Pedido>> buscarTodos(){
		List<Pedido> list = cadastroPedido.listar();
		return ResponseEntity.ok().body(list);
		
	}
	
	@GetMapping("/{pedidoId}")
	public ResponseEntity<?> buscarPorId(@PathVariable Long pedidoId) {
		try {
			PedidoDTO dto = cadastroPedido.buscarPorId(pedidoId);
				return ResponseEntity.ok(dto);
		} catch (EntidadeNaoEncontradaException e) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());
		}
		
	}


	@PutMapping("/{pedidoId}")
	public ResponseEntity<?> editar(@PathVariable Long pedidoId, @RequestBody Pedido pAntigo) {	
		try {
			Pedido pedidoAtualizado = cadastroPedido.atualizar(pedidoId, pAntigo);		
				return ResponseEntity.ok(pedidoAtualizado);		
		} catch (EntidadeNaoEncontradaException e) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());
		}
	}

	@DeleteMapping("/{peididoId}")
	public ResponseEntity<?> excluir(@PathVariable Long pedidoId) {
		try {
			cadastroPedido.excluir(pedidoId);
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

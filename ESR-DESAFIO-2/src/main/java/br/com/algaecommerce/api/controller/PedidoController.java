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
import org.springframework.web.bind.annotation.RestController;
import br.com.algaecommerce.domain.dto.PedidoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.service.CadastroPedidoService;


@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {
	
	
	@Autowired 
	CadastroPedidoService cadastroPedido;
	
	
	
	@PostMapping
	public ResponseEntity<?> cadastrarUmNovoPedido(@RequestBody PedidoDTO dto) {
		try {
			dto = cadastroPedido.salvar(dto);
			return ResponseEntity.status(HttpStatus.CREATED).body(dto);
		} catch (EntidadeNaoEncontradaException e) {
			    return ResponseEntity
			    		.status(HttpStatus.BAD_REQUEST)
			    		.body(e.getMessage());
		}
	}
	
	@GetMapping
	public List<PedidoDTO> buscarTodos(){
		return cadastroPedido.listar();
		
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
	public ResponseEntity<?> editar(@PathVariable Long pedidoId, @RequestBody PedidoDTO pAntigo) {	
		try {
			PedidoDTO pedidoAtualizado = cadastroPedido.atualizar(pedidoId, pAntigo);		
			return ResponseEntity.ok().body(pedidoAtualizado);		
		} catch (EntidadeNaoEncontradaException e) {
			    return ResponseEntity
			    		.status(HttpStatus.NOT_FOUND)
			    		.body(e.getMessage());
		}
	}

	@DeleteMapping("/{pedidoId}")
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

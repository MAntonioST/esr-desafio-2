package br.com.algaecommerce.domain.model.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algaecommerce.domain.dto.PedidoDTO;
import br.com.algaecommerce.domain.dto.ProdutoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Cliente;
import br.com.algaecommerce.domain.model.Endereco;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.repository.ClienteRepository;
import br.com.algaecommerce.domain.repository.PedidoRepository;
import br.com.algaecommerce.domain.repository.ProdutoRepository;

@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepositorio;

	@Autowired
	private ClienteRepository clienteRepositorio;
	
	@Autowired
	private ProdutoRepository produtoRepositorio;

	
	@Transactional
	public PedidoDTO salvar(PedidoDTO dto) {
		Pedido pedido = new Pedido();
		pedido.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		Cliente cliente = clienteRepositorio.findById(dto.getCliente().getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Cliente com código %d", dto.getCliente().getId())));
	    List<Endereco> end = cliente.getPedidos()
						    		.stream()
						    		.map(e -> e.getEnderecoEntrega())
						    		.collect(Collectors.toList());
			
		pedido.setEnderecoEntrega(end.get(0));
		pedido.setCliente(dto.getCliente());
		
		for(ProdutoDTO p : dto.getProdutoList()) {
			  Produto produto = produtoRepositorio.findById(p.getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(
						String.format("Não existe um cadastro de Produto com código %d", p.getId())));
			  pedido.getProdutoList().add(produto);
			}
		pedido = pedidoRepositorio.save(pedido);
		return new PedidoDTO(pedido);
	}

	@Transactional(readOnly = true)
	public List<PedidoDTO> listar() {
		List<Pedido> list =  pedidoRepositorio.findAll();
		return list.stream()
				   .map(p -> new PedidoDTO(p, p.getProdutoList()))
				   .distinct()
				   .collect(Collectors.toList());
	}

	@Transactional
	public PedidoDTO buscarPorId(Long pedidoId) {
		Optional<Pedido> obj = pedidoRepositorio.findById(pedidoId);
		Pedido entidade = obj.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Pedido com código %d", pedidoId)));
		return new PedidoDTO(entidade);

	}

	public PedidoDTO atualizar(Long pedidoId, PedidoDTO pAntigo) {
		Pedido pNoBanco = pedidoRepositorio.findById(pedidoId).orElse(null);
		convertDtoToEntity(pAntigo, pNoBanco);
		BeanUtils.copyProperties(pNoBanco, pAntigo, "id","cliente", "enderecoEntrega","dataCriacao");
		
		pNoBanco = pedidoRepositorio.save(pNoBanco);
		return new PedidoDTO(pNoBanco, pNoBanco.getProdutoList());

	}

	public void excluir(Long pedidoId) {
		try {
			pedidoRepositorio.deleteById(pedidoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Pedido com código %d", pedidoId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Pedido de código %d não pode ser removida, pois está em uso", pedidoId));
		}
	}


	private void convertDtoToEntity(PedidoDTO dto, Pedido entidade) {
		entidade.setCliente(dto.getCliente());
		entidade.setEnderecoEntrega(dto.getEnderecoEntrega());;
		entidade.getProdutoList().clear();
		for(ProdutoDTO proDTO : dto.getProdutoList()) {
			Produto p = produtoRepositorio.getOne(proDTO.getId());
			entidade.getProdutoList().add(p);
		}
		
	}
}

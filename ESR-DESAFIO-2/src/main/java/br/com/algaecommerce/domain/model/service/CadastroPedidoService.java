package br.com.algaecommerce.domain.model.service;


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
import br.com.algaecommerce.domain.model.enums.TipoEndereco;
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
		Cliente cliente = clienteRepositorio.findById(dto.getCliente().getId()).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Cliente com código %d", dto.getCliente().getId())));
	    
		Endereco end = cliente.getEnderecos().get(TipoEndereco.ENDERECO_ENTREGA);
						    				
		pedido.setEnderecoEntrega(end);
		pedido.setCliente(dto.getCliente());
		
		for(ProdutoDTO p : dto.getProdutoList()) {
			  Produto produto = produtoRepositorio.getOne(p.getId());
			  pedido.getProdutoList().add(produto);
			}
		pedido = pedidoRepositorio.save(pedido);
		return new PedidoDTO(pedido);
	}

	@Transactional(readOnly = true)
	public List<PedidoDTO> listar() {
		List<Pedido> list =  pedidoRepositorio.findAll();
		return list.stream()
				   .distinct()
				   .map(p -> new PedidoDTO(p, p.getProdutoList()))
				   .collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public PedidoDTO buscarPorId(Long pedidoId) {
		Optional<Pedido> obj = pedidoRepositorio.findById(pedidoId);
		Pedido entidade = obj.orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Pedido com código %d", pedidoId)));
		return new PedidoDTO(entidade);

	}

	@Transactional
	public PedidoDTO atualizar(Long pedidoId, PedidoDTO dto) {
		Pedido pedido = new Pedido();
		Pedido pedidoAtual = pedidoRepositorio.findById(pedidoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Pedido com código %d", pedidoId)));;
		converteDtoParaEntidade(dto, pedido);
		BeanUtils.copyProperties(pedido, pedidoAtual, "id","cliente", "enderecoEntrega","dataCriacao");
		
		pedidoAtual = pedidoRepositorio.save(pedidoAtual);
		return new PedidoDTO(pedidoAtual, pedidoAtual.getProdutoList());

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


	private void converteDtoParaEntidade(PedidoDTO dto, Pedido entidade) {
		entidade.setCliente(dto.getCliente());
		entidade.setEnderecoEntrega(dto.getEnderecoEntrega());
		entidade.getProdutoList().clear();
		for(ProdutoDTO proDTO : dto.getProdutoList()) {
			Produto p = produtoRepositorio.getOne(proDTO.getId());
			entidade.getProdutoList().add(p);
		}
		
	}
}

package br.com.algaecommerce.domain.model.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algaecommerce.domain.dto.PedidoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.repository.PedidoRepository;


@Service
public class CadastroPedidoService {

	@Autowired
	private PedidoRepository pedidoRepositorio;

	@Transactional
	public Pedido salvar(Pedido pedido) {
		pedido.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		return pedidoRepositorio.save(pedido);
	}

	public List<Pedido> listar() {
		return pedidoRepositorio.findAll();
	}

	@Transactional
	public PedidoDTO buscarPorId(Long pedidoId) {
		Pedido pedido = pedidoRepositorio.findById(pedidoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Pedido com código %d", pedidoId)));
	   
		
		return new PedidoDTO(pedido);
	}

	
	public Pedido atualizar(Long pedidoId, Pedido pAntigo) {
		    Pedido pNoBanco = pedidoRepositorio.findById(pedidoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Pedido com código %d", pedidoId)));
			BeanUtils.copyProperties(pAntigo, pNoBanco, "id", "dataCriacao", "tags");
			Pedido PedidoSalvo = pedidoRepositorio.save(pNoBanco);
			return PedidoSalvo;
	
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
}

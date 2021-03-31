package br.com.algaecommerce.domain.model.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.algaecommerce.domain.dto.ProdutoDTO;
import br.com.algaecommerce.domain.exception.EntidadeEmUsoException;
import br.com.algaecommerce.domain.exception.EntidadeNaoEncontradaException;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.repository.ProdutoRepository;


@Service
public class CadastroProdutoService {

	@Autowired
	private ProdutoRepository produtoRepositorio;

	@Transactional
	public Produto salvar(Produto produto) {
		produto.setDataCriacao(LocalDateTime.now(ZoneId.systemDefault()));
		return produtoRepositorio.save(produto);
	}

	public List<ProdutoDTO> listar() {
		List<Produto> produtos = produtoRepositorio.findAll();
		return produtos.stream()
				       .map(p -> new  ProdutoDTO(p))
				       .collect(Collectors.toList());
	}

	@Transactional
	public ProdutoDTO buscarPorId(Long produtoId) {
		Produto produto = produtoRepositorio.findById(produtoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Produto com código %d", produtoId)));
	    return new ProdutoDTO(produto);
	}

	public Produto atualizar(Long produtoId, Produto pAntigo) {
		   Produto pNoBanco = produtoRepositorio.findById(produtoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Produto com código %d", produtoId)));
			BeanUtils.copyProperties(pAntigo, pNoBanco, "id", "dataCriacao", "tags");
			Produto produtoSalvo = produtoRepositorio.save(pNoBanco);
			return produtoSalvo;
	
	}

	
	public void excluir(Long ProdutoId) {
		try {
			produtoRepositorio.deleteById(ProdutoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe um cadastro de Produto com código %d", ProdutoId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Produto de código %d não pode ser removida, pois está em uso", ProdutoId));
		}
	}
}

package br.com.algaecommerce.domain.model.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

	public List<Produto> listar() {
		return produtoRepositorio.findAll();
	}

	@Transactional
	public Produto buscarPorId(Long produtoId) {
		return produtoRepositorio.findById(produtoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
				String.format("Não existe um cadastro de Produto com código %d", produtoId)));
	}

	public Produto atualizar(Long produtoId, Produto pAntigo) {

		   Produto pNoBanco = buscarPorId(produtoId);

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

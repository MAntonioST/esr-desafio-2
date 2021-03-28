package br.com.algaecommerce.domain.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.algaecommerce.domain.model.Produto;



public class ProdutoDTO {

	
	private Long id;
	private String nome;
	private LocalDateTime dataCriacao;
	private List<String> tags = new ArrayList<>();
	

	public ProdutoDTO() {
	
	}


	public ProdutoDTO(Long id, String nome, LocalDateTime dataCriacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataCriacao = dataCriacao;
	}

	public ProdutoDTO(Produto entidade) {
		id = entidade.getId();
		nome = entidade.getNome();
		dataCriacao =  entidade.getDataCriacao();
		tags = entidade.getTags();
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}


	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}


	public List<String> getTags() {
		return tags;
	}


	public void setTags(List<String> tags) {
		this.tags = tags;
	}
	
	
}

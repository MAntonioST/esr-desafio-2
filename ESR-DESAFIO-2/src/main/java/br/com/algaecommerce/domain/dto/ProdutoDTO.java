package br.com.algaecommerce.domain.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import br.com.algaecommerce.domain.model.Produto;



public class ProdutoDTO {

	
	private Long id;
	private String nome;
	private LocalDateTime dataCriacao;
	private Set<String> tags = new HashSet<>();
	

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


	public Set<String> getTags() {
		return tags;
	}


	public void setTags(Set<String> tags) {
		this.tags = tags;
	}


	
}

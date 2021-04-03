package br.com.algaecommerce.domain.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@CreationTimestamp
	@Column(nullable = false)
	private LocalDateTime dataCriacao;
	
	@JsonIgnore
	@ManyToMany
	private List<Produto> produtoList = new ArrayList<>();
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	private Cliente cliente;
	
	@JsonIgnore
	@Embedded
	private Endereco enderecoEntrega;
	
	public Pedido() {
	
	}
	

	public Pedido(Long id, LocalDateTime dataCriacao, Cliente cliente, Endereco enderecoEntrega) {
		super();
		this.id = id;
		this.dataCriacao = dataCriacao;
		this.cliente = cliente;
		this.enderecoEntrega = enderecoEntrega;
	}



	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}
	
	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}
	

	public List<Produto> getProdutoList() {
		return produtoList;
	}


	public void setProdutoList(List<Produto> produtoList) {
		this.produtoList = produtoList;
	}


	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
	public Endereco getEnderecoEntrega() {
		return enderecoEntrega;
	}
	
	public void setEnderecoEntrega(Endereco enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}
}

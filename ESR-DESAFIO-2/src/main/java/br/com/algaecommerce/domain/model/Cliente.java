package br.com.algaecommerce.domain.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;

import br.com.algaecommerce.domain.model.enums.TipoEndereco;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq2")
	@SequenceGenerator(name = "seq2", sequenceName = "seq_chave_prim")
	private Long id;
	
	private String nome;
	
	
	@ElementCollection(targetClass = Endereco.class)
	@CollectionTable(name = "cliente_endereco", 
	                 joinColumns = @JoinColumn(name = "cliente_id"))
	@MapKeyColumn(name="tipo")
	@MapKeyEnumerated(EnumType.STRING)
	private Map<TipoEndereco, Endereco> enderecos = new HashMap<>();
	
	
	@OneToMany(mappedBy = "cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Long getId() {
		return id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	

	public Map<TipoEndereco, Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Map<TipoEndereco, Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}
	
	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}
}

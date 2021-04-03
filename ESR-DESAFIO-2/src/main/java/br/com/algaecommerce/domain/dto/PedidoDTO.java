package br.com.algaecommerce.domain.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.algaecommerce.domain.model.Cliente;
import br.com.algaecommerce.domain.model.Endereco;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.Produto;



public class PedidoDTO {

	
	private Long pedidoId;
	private Cliente cliente;
	private Endereco enderecoEntrega;
	private List<ProdutoDTO> produtoList = new ArrayList<>();
	
	
	public PedidoDTO() {
	
	}
	
	
	public PedidoDTO(Long pedidoId, Cliente cliente, Endereco enderecoEntrega) {
		super();
		this.pedidoId = pedidoId;
		this.cliente = cliente;
		this.enderecoEntrega = enderecoEntrega;
	}



	public PedidoDTO(Pedido entidade) {
		pedidoId = entidade.getId();
		cliente = entidade.getCliente();
		cliente.setEnderecos(null);
		cliente.setPedidos(null);
		enderecoEntrega = entidade.getEnderecoEntrega();
		produtoList = entidade.getProdutoList().stream().distinct()
						      .map(p -> new ProdutoDTO(p))
						      .collect(Collectors.toList());
	}
	
	public PedidoDTO(Pedido entidade, List<Produto> produtos) {
		pedidoId = entidade.getId();
		cliente = entidade.getCliente();
		cliente.setEnderecos(null);
		cliente.setPedidos(null);
		produtos.forEach(p -> this.produtoList.add(new ProdutoDTO(p)));
		produtoList = produtos.stream().distinct()
						      .map(p -> new ProdutoDTO(p))
						      .collect(Collectors.toList());

	}


	public Long getPedidoId() {
		return pedidoId;
	}


	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
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


	public List<ProdutoDTO> getProdutoList() {
		return produtoList;
	}


	public void setProdutoList(List<ProdutoDTO> produtoList) {
		this.produtoList = produtoList;
	}

	

}

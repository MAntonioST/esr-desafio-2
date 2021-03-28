package br.com.algaecommerce.domain.dto;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import br.com.algaecommerce.domain.model.Endereco;
import br.com.algaecommerce.domain.model.Pedido;



public class PedidoDTO {

	
	private Long pedidoId;
	private String cliente;
	private Endereco enderecoEntrega;
	private List<ProdutoDTO> produtoList = new ArrayList<>();
	
	
	public PedidoDTO() {
	
	}
	
	
	public PedidoDTO(Long pedidoId, String cliente, Endereco enderecoEntrega) {
		super();
		this.pedidoId = pedidoId;
		this.cliente = cliente;
		this.enderecoEntrega = enderecoEntrega;
	}



	public PedidoDTO(Pedido entidade) {
		pedidoId = entidade.getId();
		cliente = entidade.getCliente().getNome();
		enderecoEntrega = entidade.getEnderecoEntrega();
		produtoList = entidade.getProdutoList().stream()
		         .map(p -> new ProdutoDTO(p))
		         .collect(Collectors.toList());
	}


	public Long getPedidoId() {
		return pedidoId;
	}


	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}


	public String getCliente() {
		return cliente;
	}


	public void setCliente(String cliente) {
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

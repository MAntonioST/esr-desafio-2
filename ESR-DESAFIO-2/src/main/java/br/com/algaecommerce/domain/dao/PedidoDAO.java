package br.com.algaecommerce.domain.dao;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.repository.PedidoRepository;

@Component
public class PedidoDAO  {
	
 @Autowired
 private PedidoRepository pedidoRepository;
	
	
	public List<Pedido> findAll() {
		return pedidoRepository.findAll();
	}
	
}

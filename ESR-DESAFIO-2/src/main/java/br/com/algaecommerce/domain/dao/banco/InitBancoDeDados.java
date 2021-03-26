package br.com.algaecommerce.domain.dao.banco;

import java.time.LocalDateTime;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import br.com.algaecommerce.domain.model.Cliente;
import br.com.algaecommerce.domain.model.Endereco;
import br.com.algaecommerce.domain.model.Pedido;
import br.com.algaecommerce.domain.model.Produto;
import br.com.algaecommerce.domain.model.enums.TipoEndereco;

@Component
public class InitBancoDeDados implements ApplicationRunner {
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	@Transactional
	public void run(ApplicationArguments args) throws Exception {
		
			Produto produto1 = new Produto();
			produto1.setDataCriacao(LocalDateTime.now());
			produto1.setNome("Desktop");
			produto1.setTags(Arrays.asList("TI", "Mesa"));
			manager.persist(produto1);
			
			
			Produto produto2 = new Produto();
			produto2.setDataCriacao(LocalDateTime.now());
			produto2.setNome("Notebook");
			produto2.setTags(Arrays.asList("TI", "Portátil", "Departamento de TI"));
			manager.persist(produto2);
			
			Endereco enderecoEntrega = new Endereco();
			enderecoEntrega.setBairro("Centro");
			enderecoEntrega.setCep("0000-000");
			enderecoEntrega.setCidade("Mogi das Cruzes");
			enderecoEntrega.setEstado("SP");
			enderecoEntrega.setComplemento("casa");
			enderecoEntrega.setLogradouro("Rua Narciso Yague Guimarães");
			enderecoEntrega.setNumero("1000");
			
			Endereco enderecoResidencial = new Endereco();
			enderecoResidencial.setBairro("Pque Santana");
			enderecoResidencial.setCep("3333-333");
			enderecoResidencial.setCidade("Mogi das Cruzes");
			enderecoResidencial.setEstado("SP");
			enderecoResidencial.setComplemento("apto");
			enderecoResidencial.setLogradouro("Rua Izidoro Bocault");
			enderecoResidencial.setNumero("1160");
			
			
			Cliente cliente1 = new Cliente();
			cliente1.setNome("João");
			cliente1.getEnderecos().put(TipoEndereco.ENDERECO_ENTREGA, enderecoEntrega);
			cliente1.getEnderecos().put(TipoEndereco.ENDERECO_RESIDENCIAL, enderecoResidencial);
			//cliente1.setEnderecos(Collections.singletonMap(TipoEndereco.ENDERECO_ENTREGA, enderecoEntrega));
			//cliente1.setEnderecos(Collections.singletonMap(TipoEndereco.ENDERECO_RESIDENCIAL, enderecoResidencial));
			manager.persist(cliente1);
			
			
			Pedido pedido = new Pedido();
			pedido.setDataCriacao(LocalDateTime.now());
			pedido.setProdutoList(Arrays.asList(produto1, produto2));
			pedido.setCliente(cliente1);
			pedido.setEnderecoEntrega(enderecoEntrega);
			manager.persist(pedido);
			
			
			Endereco enderecoEntrega1 = new Endereco();
			enderecoEntrega1.setBairro("Casa Verde");
			enderecoEntrega1.setCep("1111-111");
			enderecoEntrega1.setCidade("São Paulo");
			enderecoEntrega1.setEstado("SP");
			enderecoEntrega1.setComplemento("casa");
			enderecoEntrega1.setLogradouro("Rua Ermilino Matarazzo");
			enderecoEntrega1.setNumero("500");
			
			
			Endereco enderecoResidencial1 = new Endereco();
			enderecoResidencial1.setBairro("Vila Alpina");
			enderecoResidencial1.setCep("2222-222");
			enderecoResidencial1.setCidade("São Paulo");
			enderecoResidencial1.setEstado("SP");
			enderecoResidencial1.setComplemento("comercio");
			enderecoResidencial1.setLogradouro("Rua Bela Flor");
			enderecoResidencial1.setNumero("350");
			
			Cliente cliente2 = new Cliente();
			cliente2.setNome("Maria");
			cliente2.getEnderecos().put(TipoEndereco.ENDERECO_ENTREGA, enderecoEntrega1);
			cliente2.getEnderecos().put(TipoEndereco.ENDERECO_RESIDENCIAL, enderecoResidencial1);
			//cliente2.setEnderecos(Collections.singletonMap(TipoEndereco.ENDERECO_ENTREGA, enderecoEntrega1));
			//cliente2.setEnderecos(Collections.singletonMap(TipoEndereco.ENDERECO_RESIDENCIAL, enderecoResidencial1));
			manager.persist(cliente2);
			
			Pedido pedido2 = new Pedido();
			pedido2.setDataCriacao(LocalDateTime.now());
			pedido2.setProdutoList(Arrays.asList(produto1, produto2));
			pedido2.setCliente(cliente2);
			pedido2.setEnderecoEntrega(enderecoEntrega1);;
			manager.persist(pedido2);
	
	}
}

package br.com.algaecommerce.domain.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.algaecommerce.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	
	@Query("FROM Pedido p JOIN FETCH p.cliente JOIN FETCH p.produtoList t  JOIN FETCH t.tags ORDER BY p.id ASC")
	List<Pedido> findAll();
	
	
	@Query("FROM Pedido p JOIN FETCH p.cliente "
			+ "JOIN FETCH p.produtoList t JOIN FETCH t.tags WHERE  p.id = :id ")
	Optional<Pedido> findById(@Param("id")Long id);


	
	
} 

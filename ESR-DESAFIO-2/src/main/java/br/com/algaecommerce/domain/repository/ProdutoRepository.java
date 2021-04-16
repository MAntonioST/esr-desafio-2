package br.com.algaecommerce.domain.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import br.com.algaecommerce.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long>{
	
	@Query("FROM Produto p JOIN FETCH p.tags WHERE  p.id = :id ")
	Optional<Produto> findById(Long id);
	
	@Query("FROM Produto p  JOIN FETCH p.tags")
	List<Produto> findAll();
}

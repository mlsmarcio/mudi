package br.com.alura.mvc.mudi.repository;

import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.model.StatusPedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>{
	@Cacheable("cachePedidos")
	Page<Pedido> findByStatus(StatusPedido status, Pageable paginacao);
	
	@Query("select p from Pedido p join p.user u where u.username != :username and p.status = :status")
	List<Pedido> findByStatusEOthersUsuario(@Param("username")String username, 
										@Param("status")StatusPedido status, 
										Pageable paginacao);
	
	@Query("select p from Pedido p join p.user u where u.username = :username")
	Page<Pedido>  findAllByUsuario(@Param("username")String username, Pageable paginacao);
	
	@Cacheable("cachePedidosStatus")
	@Query("select p from Pedido p join p.user u where u.username = :username and p.status = :status")
	Page<Pedido> findByStatusEUsuario(@Param("status")StatusPedido status, @Param("username")String username, Pageable paginacao);
}

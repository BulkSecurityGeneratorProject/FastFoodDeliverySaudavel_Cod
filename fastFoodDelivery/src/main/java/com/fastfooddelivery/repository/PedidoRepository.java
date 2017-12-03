package com.fastfooddelivery.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fastfooddelivery.domain.Pedido;
import com.fastfooddelivery.domain.Pessoa;

/**
 * Spring Data JPA repository for the Pedido entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long>, JpaSpecificationExecutor<Pedido> {
    @Query("select distinct pedido from Pedido pedido left join fetch pedido.bebidas left join fetch pedido.alimentos")
    List<Pedido> findAllWithEagerRelationships();

    @Query("select pedido from Pedido pedido left join fetch pedido.bebidas left join fetch pedido.alimentos where pedido.id =:id")
    Pedido findOneWithEagerRelationships(@Param("id") Long id);
    
    List<Pedido> findAllByPessoa(Pessoa pessoa);

}

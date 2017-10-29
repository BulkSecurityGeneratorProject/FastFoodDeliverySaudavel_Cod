package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Pedido;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

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

}

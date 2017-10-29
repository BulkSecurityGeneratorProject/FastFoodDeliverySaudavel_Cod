package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Bebida;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Bebida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BebidaRepository extends JpaRepository<Bebida, Long> {
    @Query("select distinct bebida from Bebida bebida left join fetch bebida.doces")
    List<Bebida> findAllWithEagerRelationships();

    @Query("select bebida from Bebida bebida left join fetch bebida.doces where bebida.id =:id")
    Bebida findOneWithEagerRelationships(@Param("id") Long id);

}

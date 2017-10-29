package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Refeicao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Refeicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    @Query("select distinct refeicao from Refeicao refeicao left join fetch refeicao.tipoAlimentos")
    List<Refeicao> findAllWithEagerRelationships();

    @Query("select refeicao from Refeicao refeicao left join fetch refeicao.tipoAlimentos where refeicao.id =:id")
    Refeicao findOneWithEagerRelationships(@Param("id") Long id);

}

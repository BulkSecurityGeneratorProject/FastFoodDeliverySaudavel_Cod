
package com.fastfooddelivery.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fastfooddelivery.domain.Alimento;
import com.fastfooddelivery.domain.TipoAlimento;

/**
 * Spring Data JPA repository for the Alimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    @Query("select distinct alimento from Alimento alimento left join fetch alimento.preparos left join fetch alimento.temperos")
    List<Alimento> findAllWithEagerRelationships();

    @Query("select alimento from Alimento alimento left join fetch alimento.preparos left join fetch alimento.temperos where alimento.id =:id")
    Alimento findOneWithEagerRelationships(@Param("id") Long id);
    
    List<Alimento> findByTipoAlimento(TipoAlimento tipoAlimento);

}

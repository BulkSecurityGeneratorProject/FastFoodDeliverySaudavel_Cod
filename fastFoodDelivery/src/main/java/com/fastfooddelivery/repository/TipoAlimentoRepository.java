package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.TipoAlimento;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoAlimento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

}

package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.FormaDeEntrega;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FormaDeEntrega entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FormaDeEntregaRepository extends JpaRepository<FormaDeEntrega, Long> {

}

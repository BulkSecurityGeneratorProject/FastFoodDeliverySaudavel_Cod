package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Cartao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Cartao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

}

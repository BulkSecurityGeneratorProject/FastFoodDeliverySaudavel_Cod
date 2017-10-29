package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.ValorRefeicao;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValorRefeicao entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValorRefeicaoRepository extends JpaRepository<ValorRefeicao, Long> {

}

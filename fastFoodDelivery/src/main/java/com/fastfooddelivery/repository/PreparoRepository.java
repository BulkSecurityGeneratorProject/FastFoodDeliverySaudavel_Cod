package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Preparo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Preparo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PreparoRepository extends JpaRepository<Preparo, Long> {

}

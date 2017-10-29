package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Doce;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Doce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoceRepository extends JpaRepository<Doce, Long> {

}

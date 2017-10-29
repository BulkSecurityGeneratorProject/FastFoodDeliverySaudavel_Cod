package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.ValorNutricional;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the ValorNutricional entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ValorNutricionalRepository extends JpaRepository<ValorNutricional, Long> {

}

package com.fastfooddelivery.repository;

import com.fastfooddelivery.domain.Tempero;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Tempero entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TemperoRepository extends JpaRepository<Tempero, Long> {

}

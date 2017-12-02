package com.fastfooddelivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastfooddelivery.domain.Pessoa;
import com.fastfooddelivery.domain.User;


/**
 * Spring Data JPA repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
	
	Optional<Pessoa> findOneByUser(User user);

}

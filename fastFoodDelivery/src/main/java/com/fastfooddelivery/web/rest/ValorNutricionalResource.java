package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.ValorNutricional;

import com.fastfooddelivery.repository.ValorNutricionalRepository;
import com.fastfooddelivery.web.rest.errors.BadRequestAlertException;
import com.fastfooddelivery.web.rest.util.HeaderUtil;
import com.fastfooddelivery.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ValorNutricional.
 */
@RestController
@RequestMapping("/api")
public class ValorNutricionalResource {

    private final Logger log = LoggerFactory.getLogger(ValorNutricionalResource.class);

    private static final String ENTITY_NAME = "valorNutricional";

    private final ValorNutricionalRepository valorNutricionalRepository;

    public ValorNutricionalResource(ValorNutricionalRepository valorNutricionalRepository) {
        this.valorNutricionalRepository = valorNutricionalRepository;
    }

    /**
     * POST  /valor-nutricionals : Create a new valorNutricional.
     *
     * @param valorNutricional the valorNutricional to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valorNutricional, or with status 400 (Bad Request) if the valorNutricional has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valor-nutricionals")
    @Timed
    public ResponseEntity<ValorNutricional> createValorNutricional(@RequestBody ValorNutricional valorNutricional) throws URISyntaxException {
        log.debug("REST request to save ValorNutricional : {}", valorNutricional);
        if (valorNutricional.getId() != null) {
            throw new BadRequestAlertException("A new valorNutricional cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValorNutricional result = valorNutricionalRepository.save(valorNutricional);
        return ResponseEntity.created(new URI("/api/valor-nutricionals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valor-nutricionals : Updates an existing valorNutricional.
     *
     * @param valorNutricional the valorNutricional to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valorNutricional,
     * or with status 400 (Bad Request) if the valorNutricional is not valid,
     * or with status 500 (Internal Server Error) if the valorNutricional couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valor-nutricionals")
    @Timed
    public ResponseEntity<ValorNutricional> updateValorNutricional(@RequestBody ValorNutricional valorNutricional) throws URISyntaxException {
        log.debug("REST request to update ValorNutricional : {}", valorNutricional);
        if (valorNutricional.getId() == null) {
            return createValorNutricional(valorNutricional);
        }
        ValorNutricional result = valorNutricionalRepository.save(valorNutricional);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valorNutricional.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valor-nutricionals : get all the valorNutricionals.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of valorNutricionals in body
     */
    @GetMapping("/valor-nutricionals")
    @Timed
    public ResponseEntity<List<ValorNutricional>> getAllValorNutricionals(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ValorNutricionals");
        Page<ValorNutricional> page = valorNutricionalRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/valor-nutricionals");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /valor-nutricionals/:id : get the "id" valorNutricional.
     *
     * @param id the id of the valorNutricional to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valorNutricional, or with status 404 (Not Found)
     */
    @GetMapping("/valor-nutricionals/{id}")
    @Timed
    public ResponseEntity<ValorNutricional> getValorNutricional(@PathVariable Long id) {
        log.debug("REST request to get ValorNutricional : {}", id);
        ValorNutricional valorNutricional = valorNutricionalRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valorNutricional));
    }

    /**
     * DELETE  /valor-nutricionals/:id : delete the "id" valorNutricional.
     *
     * @param id the id of the valorNutricional to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valor-nutricionals/{id}")
    @Timed
    public ResponseEntity<Void> deleteValorNutricional(@PathVariable Long id) {
        log.debug("REST request to delete ValorNutricional : {}", id);
        valorNutricionalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

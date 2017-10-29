package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Bebida;

import com.fastfooddelivery.repository.BebidaRepository;
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
 * REST controller for managing Bebida.
 */
@RestController
@RequestMapping("/api")
public class BebidaResource {

    private final Logger log = LoggerFactory.getLogger(BebidaResource.class);

    private static final String ENTITY_NAME = "bebida";

    private final BebidaRepository bebidaRepository;

    public BebidaResource(BebidaRepository bebidaRepository) {
        this.bebidaRepository = bebidaRepository;
    }

    /**
     * POST  /bebidas : Create a new bebida.
     *
     * @param bebida the bebida to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bebida, or with status 400 (Bad Request) if the bebida has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bebidas")
    @Timed
    public ResponseEntity<Bebida> createBebida(@RequestBody Bebida bebida) throws URISyntaxException {
        log.debug("REST request to save Bebida : {}", bebida);
        if (bebida.getId() != null) {
            throw new BadRequestAlertException("A new bebida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Bebida result = bebidaRepository.save(bebida);
        return ResponseEntity.created(new URI("/api/bebidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bebidas : Updates an existing bebida.
     *
     * @param bebida the bebida to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bebida,
     * or with status 400 (Bad Request) if the bebida is not valid,
     * or with status 500 (Internal Server Error) if the bebida couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bebidas")
    @Timed
    public ResponseEntity<Bebida> updateBebida(@RequestBody Bebida bebida) throws URISyntaxException {
        log.debug("REST request to update Bebida : {}", bebida);
        if (bebida.getId() == null) {
            return createBebida(bebida);
        }
        Bebida result = bebidaRepository.save(bebida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bebida.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bebidas : get all the bebidas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bebidas in body
     */
    @GetMapping("/bebidas")
    @Timed
    public ResponseEntity<List<Bebida>> getAllBebidas(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Bebidas");
        Page<Bebida> page = bebidaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bebidas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bebidas/:id : get the "id" bebida.
     *
     * @param id the id of the bebida to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bebida, or with status 404 (Not Found)
     */
    @GetMapping("/bebidas/{id}")
    @Timed
    public ResponseEntity<Bebida> getBebida(@PathVariable Long id) {
        log.debug("REST request to get Bebida : {}", id);
        Bebida bebida = bebidaRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bebida));
    }

    /**
     * DELETE  /bebidas/:id : delete the "id" bebida.
     *
     * @param id the id of the bebida to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bebidas/{id}")
    @Timed
    public ResponseEntity<Void> deleteBebida(@PathVariable Long id) {
        log.debug("REST request to delete Bebida : {}", id);
        bebidaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Tempero;

import com.fastfooddelivery.repository.TemperoRepository;
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
 * REST controller for managing Tempero.
 */
@RestController
@RequestMapping("/api")
public class TemperoResource {

    private final Logger log = LoggerFactory.getLogger(TemperoResource.class);

    private static final String ENTITY_NAME = "tempero";

    private final TemperoRepository temperoRepository;

    public TemperoResource(TemperoRepository temperoRepository) {
        this.temperoRepository = temperoRepository;
    }

    /**
     * POST  /temperos : Create a new tempero.
     *
     * @param tempero the tempero to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tempero, or with status 400 (Bad Request) if the tempero has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/temperos")
    @Timed
    public ResponseEntity<Tempero> createTempero(@RequestBody Tempero tempero) throws URISyntaxException {
        log.debug("REST request to save Tempero : {}", tempero);
        if (tempero.getId() != null) {
            throw new BadRequestAlertException("A new tempero cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tempero result = temperoRepository.save(tempero);
        return ResponseEntity.created(new URI("/api/temperos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /temperos : Updates an existing tempero.
     *
     * @param tempero the tempero to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tempero,
     * or with status 400 (Bad Request) if the tempero is not valid,
     * or with status 500 (Internal Server Error) if the tempero couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/temperos")
    @Timed
    public ResponseEntity<Tempero> updateTempero(@RequestBody Tempero tempero) throws URISyntaxException {
        log.debug("REST request to update Tempero : {}", tempero);
        if (tempero.getId() == null) {
            return createTempero(tempero);
        }
        Tempero result = temperoRepository.save(tempero);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tempero.getId().toString()))
            .body(result);
    }

    /**
     * GET  /temperos : get all the temperos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of temperos in body
     */
    @GetMapping("/temperos")
    @Timed
    public ResponseEntity<List<Tempero>> getAllTemperos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Temperos");
        Page<Tempero> page = temperoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/temperos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /temperos/:id : get the "id" tempero.
     *
     * @param id the id of the tempero to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tempero, or with status 404 (Not Found)
     */
    @GetMapping("/temperos/{id}")
    @Timed
    public ResponseEntity<Tempero> getTempero(@PathVariable Long id) {
        log.debug("REST request to get Tempero : {}", id);
        Tempero tempero = temperoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tempero));
    }

    /**
     * DELETE  /temperos/:id : delete the "id" tempero.
     *
     * @param id the id of the tempero to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/temperos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTempero(@PathVariable Long id) {
        log.debug("REST request to delete Tempero : {}", id);
        temperoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

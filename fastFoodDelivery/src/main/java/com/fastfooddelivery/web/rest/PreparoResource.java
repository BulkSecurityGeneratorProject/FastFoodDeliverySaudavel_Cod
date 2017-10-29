package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Preparo;

import com.fastfooddelivery.repository.PreparoRepository;
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
 * REST controller for managing Preparo.
 */
@RestController
@RequestMapping("/api")
public class PreparoResource {

    private final Logger log = LoggerFactory.getLogger(PreparoResource.class);

    private static final String ENTITY_NAME = "preparo";

    private final PreparoRepository preparoRepository;

    public PreparoResource(PreparoRepository preparoRepository) {
        this.preparoRepository = preparoRepository;
    }

    /**
     * POST  /preparos : Create a new preparo.
     *
     * @param preparo the preparo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preparo, or with status 400 (Bad Request) if the preparo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/preparos")
    @Timed
    public ResponseEntity<Preparo> createPreparo(@RequestBody Preparo preparo) throws URISyntaxException {
        log.debug("REST request to save Preparo : {}", preparo);
        if (preparo.getId() != null) {
            throw new BadRequestAlertException("A new preparo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Preparo result = preparoRepository.save(preparo);
        return ResponseEntity.created(new URI("/api/preparos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /preparos : Updates an existing preparo.
     *
     * @param preparo the preparo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preparo,
     * or with status 400 (Bad Request) if the preparo is not valid,
     * or with status 500 (Internal Server Error) if the preparo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/preparos")
    @Timed
    public ResponseEntity<Preparo> updatePreparo(@RequestBody Preparo preparo) throws URISyntaxException {
        log.debug("REST request to update Preparo : {}", preparo);
        if (preparo.getId() == null) {
            return createPreparo(preparo);
        }
        Preparo result = preparoRepository.save(preparo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preparo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /preparos : get all the preparos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of preparos in body
     */
    @GetMapping("/preparos")
    @Timed
    public ResponseEntity<List<Preparo>> getAllPreparos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Preparos");
        Page<Preparo> page = preparoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/preparos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /preparos/:id : get the "id" preparo.
     *
     * @param id the id of the preparo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preparo, or with status 404 (Not Found)
     */
    @GetMapping("/preparos/{id}")
    @Timed
    public ResponseEntity<Preparo> getPreparo(@PathVariable Long id) {
        log.debug("REST request to get Preparo : {}", id);
        Preparo preparo = preparoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preparo));
    }

    /**
     * DELETE  /preparos/:id : delete the "id" preparo.
     *
     * @param id the id of the preparo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/preparos/{id}")
    @Timed
    public ResponseEntity<Void> deletePreparo(@PathVariable Long id) {
        log.debug("REST request to delete Preparo : {}", id);
        preparoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

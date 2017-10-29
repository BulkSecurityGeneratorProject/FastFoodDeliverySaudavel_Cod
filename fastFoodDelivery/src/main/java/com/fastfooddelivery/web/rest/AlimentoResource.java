package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Alimento;

import com.fastfooddelivery.repository.AlimentoRepository;
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
 * REST controller for managing Alimento.
 */
@RestController
@RequestMapping("/api")
public class AlimentoResource {

    private final Logger log = LoggerFactory.getLogger(AlimentoResource.class);

    private static final String ENTITY_NAME = "alimento";

    private final AlimentoRepository alimentoRepository;

    public AlimentoResource(AlimentoRepository alimentoRepository) {
        this.alimentoRepository = alimentoRepository;
    }

    /**
     * POST  /alimentos : Create a new alimento.
     *
     * @param alimento the alimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new alimento, or with status 400 (Bad Request) if the alimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/alimentos")
    @Timed
    public ResponseEntity<Alimento> createAlimento(@RequestBody Alimento alimento) throws URISyntaxException {
        log.debug("REST request to save Alimento : {}", alimento);
        if (alimento.getId() != null) {
            throw new BadRequestAlertException("A new alimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Alimento result = alimentoRepository.save(alimento);
        return ResponseEntity.created(new URI("/api/alimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /alimentos : Updates an existing alimento.
     *
     * @param alimento the alimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated alimento,
     * or with status 400 (Bad Request) if the alimento is not valid,
     * or with status 500 (Internal Server Error) if the alimento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/alimentos")
    @Timed
    public ResponseEntity<Alimento> updateAlimento(@RequestBody Alimento alimento) throws URISyntaxException {
        log.debug("REST request to update Alimento : {}", alimento);
        if (alimento.getId() == null) {
            return createAlimento(alimento);
        }
        Alimento result = alimentoRepository.save(alimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, alimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /alimentos : get all the alimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of alimentos in body
     */
    @GetMapping("/alimentos")
    @Timed
    public ResponseEntity<List<Alimento>> getAllAlimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Alimentos");
        Page<Alimento> page = alimentoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/alimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /alimentos/:id : get the "id" alimento.
     *
     * @param id the id of the alimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the alimento, or with status 404 (Not Found)
     */
    @GetMapping("/alimentos/{id}")
    @Timed
    public ResponseEntity<Alimento> getAlimento(@PathVariable Long id) {
        log.debug("REST request to get Alimento : {}", id);
        Alimento alimento = alimentoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(alimento));
    }

    /**
     * DELETE  /alimentos/:id : delete the "id" alimento.
     *
     * @param id the id of the alimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/alimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteAlimento(@PathVariable Long id) {
        log.debug("REST request to delete Alimento : {}", id);
        alimentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Refeicao;

import com.fastfooddelivery.repository.RefeicaoRepository;
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
 * REST controller for managing Refeicao.
 */
@RestController
@RequestMapping("/api")
public class RefeicaoResource {

    private final Logger log = LoggerFactory.getLogger(RefeicaoResource.class);

    private static final String ENTITY_NAME = "refeicao";

    private final RefeicaoRepository refeicaoRepository;

    public RefeicaoResource(RefeicaoRepository refeicaoRepository) {
        this.refeicaoRepository = refeicaoRepository;
    }

    /**
     * POST  /refeicaos : Create a new refeicao.
     *
     * @param refeicao the refeicao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new refeicao, or with status 400 (Bad Request) if the refeicao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/refeicaos")
    @Timed
    public ResponseEntity<Refeicao> createRefeicao(@RequestBody Refeicao refeicao) throws URISyntaxException {
        log.debug("REST request to save Refeicao : {}", refeicao);
        if (refeicao.getId() != null) {
            throw new BadRequestAlertException("A new refeicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Refeicao result = refeicaoRepository.save(refeicao);
        return ResponseEntity.created(new URI("/api/refeicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /refeicaos : Updates an existing refeicao.
     *
     * @param refeicao the refeicao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated refeicao,
     * or with status 400 (Bad Request) if the refeicao is not valid,
     * or with status 500 (Internal Server Error) if the refeicao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/refeicaos")
    @Timed
    public ResponseEntity<Refeicao> updateRefeicao(@RequestBody Refeicao refeicao) throws URISyntaxException {
        log.debug("REST request to update Refeicao : {}", refeicao);
        if (refeicao.getId() == null) {
            return createRefeicao(refeicao);
        }
        Refeicao result = refeicaoRepository.save(refeicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, refeicao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /refeicaos : get all the refeicaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of refeicaos in body
     */
    @GetMapping("/refeicaos")
    @Timed
    public ResponseEntity<List<Refeicao>> getAllRefeicaos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Refeicaos");
        Page<Refeicao> page = refeicaoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/refeicaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /refeicaos/:id : get the "id" refeicao.
     *
     * @param id the id of the refeicao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the refeicao, or with status 404 (Not Found)
     */
    @GetMapping("/refeicaos/{id}")
    @Timed
    public ResponseEntity<Refeicao> getRefeicao(@PathVariable Long id) {
        log.debug("REST request to get Refeicao : {}", id);
        Refeicao refeicao = refeicaoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(refeicao));
    }

    /**
     * DELETE  /refeicaos/:id : delete the "id" refeicao.
     *
     * @param id the id of the refeicao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/refeicaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteRefeicao(@PathVariable Long id) {
        log.debug("REST request to delete Refeicao : {}", id);
        refeicaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

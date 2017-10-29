package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.ValorRefeicao;

import com.fastfooddelivery.repository.ValorRefeicaoRepository;
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
 * REST controller for managing ValorRefeicao.
 */
@RestController
@RequestMapping("/api")
public class ValorRefeicaoResource {

    private final Logger log = LoggerFactory.getLogger(ValorRefeicaoResource.class);

    private static final String ENTITY_NAME = "valorRefeicao";

    private final ValorRefeicaoRepository valorRefeicaoRepository;

    public ValorRefeicaoResource(ValorRefeicaoRepository valorRefeicaoRepository) {
        this.valorRefeicaoRepository = valorRefeicaoRepository;
    }

    /**
     * POST  /valor-refeicaos : Create a new valorRefeicao.
     *
     * @param valorRefeicao the valorRefeicao to create
     * @return the ResponseEntity with status 201 (Created) and with body the new valorRefeicao, or with status 400 (Bad Request) if the valorRefeicao has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/valor-refeicaos")
    @Timed
    public ResponseEntity<ValorRefeicao> createValorRefeicao(@RequestBody ValorRefeicao valorRefeicao) throws URISyntaxException {
        log.debug("REST request to save ValorRefeicao : {}", valorRefeicao);
        if (valorRefeicao.getId() != null) {
            throw new BadRequestAlertException("A new valorRefeicao cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ValorRefeicao result = valorRefeicaoRepository.save(valorRefeicao);
        return ResponseEntity.created(new URI("/api/valor-refeicaos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /valor-refeicaos : Updates an existing valorRefeicao.
     *
     * @param valorRefeicao the valorRefeicao to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated valorRefeicao,
     * or with status 400 (Bad Request) if the valorRefeicao is not valid,
     * or with status 500 (Internal Server Error) if the valorRefeicao couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/valor-refeicaos")
    @Timed
    public ResponseEntity<ValorRefeicao> updateValorRefeicao(@RequestBody ValorRefeicao valorRefeicao) throws URISyntaxException {
        log.debug("REST request to update ValorRefeicao : {}", valorRefeicao);
        if (valorRefeicao.getId() == null) {
            return createValorRefeicao(valorRefeicao);
        }
        ValorRefeicao result = valorRefeicaoRepository.save(valorRefeicao);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, valorRefeicao.getId().toString()))
            .body(result);
    }

    /**
     * GET  /valor-refeicaos : get all the valorRefeicaos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of valorRefeicaos in body
     */
    @GetMapping("/valor-refeicaos")
    @Timed
    public ResponseEntity<List<ValorRefeicao>> getAllValorRefeicaos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ValorRefeicaos");
        Page<ValorRefeicao> page = valorRefeicaoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/valor-refeicaos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /valor-refeicaos/:id : get the "id" valorRefeicao.
     *
     * @param id the id of the valorRefeicao to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the valorRefeicao, or with status 404 (Not Found)
     */
    @GetMapping("/valor-refeicaos/{id}")
    @Timed
    public ResponseEntity<ValorRefeicao> getValorRefeicao(@PathVariable Long id) {
        log.debug("REST request to get ValorRefeicao : {}", id);
        ValorRefeicao valorRefeicao = valorRefeicaoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(valorRefeicao));
    }

    /**
     * DELETE  /valor-refeicaos/:id : delete the "id" valorRefeicao.
     *
     * @param id the id of the valorRefeicao to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/valor-refeicaos/{id}")
    @Timed
    public ResponseEntity<Void> deleteValorRefeicao(@PathVariable Long id) {
        log.debug("REST request to delete ValorRefeicao : {}", id);
        valorRefeicaoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

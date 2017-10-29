package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.TipoAlimento;

import com.fastfooddelivery.repository.TipoAlimentoRepository;
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
 * REST controller for managing TipoAlimento.
 */
@RestController
@RequestMapping("/api")
public class TipoAlimentoResource {

    private final Logger log = LoggerFactory.getLogger(TipoAlimentoResource.class);

    private static final String ENTITY_NAME = "tipoAlimento";

    private final TipoAlimentoRepository tipoAlimentoRepository;

    public TipoAlimentoResource(TipoAlimentoRepository tipoAlimentoRepository) {
        this.tipoAlimentoRepository = tipoAlimentoRepository;
    }

    /**
     * POST  /tipo-alimentos : Create a new tipoAlimento.
     *
     * @param tipoAlimento the tipoAlimento to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoAlimento, or with status 400 (Bad Request) if the tipoAlimento has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-alimentos")
    @Timed
    public ResponseEntity<TipoAlimento> createTipoAlimento(@RequestBody TipoAlimento tipoAlimento) throws URISyntaxException {
        log.debug("REST request to save TipoAlimento : {}", tipoAlimento);
        if (tipoAlimento.getId() != null) {
            throw new BadRequestAlertException("A new tipoAlimento cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoAlimento result = tipoAlimentoRepository.save(tipoAlimento);
        return ResponseEntity.created(new URI("/api/tipo-alimentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-alimentos : Updates an existing tipoAlimento.
     *
     * @param tipoAlimento the tipoAlimento to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoAlimento,
     * or with status 400 (Bad Request) if the tipoAlimento is not valid,
     * or with status 500 (Internal Server Error) if the tipoAlimento couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-alimentos")
    @Timed
    public ResponseEntity<TipoAlimento> updateTipoAlimento(@RequestBody TipoAlimento tipoAlimento) throws URISyntaxException {
        log.debug("REST request to update TipoAlimento : {}", tipoAlimento);
        if (tipoAlimento.getId() == null) {
            return createTipoAlimento(tipoAlimento);
        }
        TipoAlimento result = tipoAlimentoRepository.save(tipoAlimento);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoAlimento.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-alimentos : get all the tipoAlimentos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tipoAlimentos in body
     */
    @GetMapping("/tipo-alimentos")
    @Timed
    public ResponseEntity<List<TipoAlimento>> getAllTipoAlimentos(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of TipoAlimentos");
        Page<TipoAlimento> page = tipoAlimentoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tipo-alimentos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tipo-alimentos/:id : get the "id" tipoAlimento.
     *
     * @param id the id of the tipoAlimento to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoAlimento, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-alimentos/{id}")
    @Timed
    public ResponseEntity<TipoAlimento> getTipoAlimento(@PathVariable Long id) {
        log.debug("REST request to get TipoAlimento : {}", id);
        TipoAlimento tipoAlimento = tipoAlimentoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoAlimento));
    }

    /**
     * DELETE  /tipo-alimentos/:id : delete the "id" tipoAlimento.
     *
     * @param id the id of the tipoAlimento to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-alimentos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoAlimento(@PathVariable Long id) {
        log.debug("REST request to delete TipoAlimento : {}", id);
        tipoAlimentoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

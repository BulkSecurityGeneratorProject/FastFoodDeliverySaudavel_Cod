package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.FormaDeEntrega;

import com.fastfooddelivery.repository.FormaDeEntregaRepository;
import com.fastfooddelivery.web.rest.errors.BadRequestAlertException;
import com.fastfooddelivery.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing FormaDeEntrega.
 */
@RestController
@RequestMapping("/api")
public class FormaDeEntregaResource {

    private final Logger log = LoggerFactory.getLogger(FormaDeEntregaResource.class);

    private static final String ENTITY_NAME = "formaDeEntrega";

    private final FormaDeEntregaRepository formaDeEntregaRepository;

    public FormaDeEntregaResource(FormaDeEntregaRepository formaDeEntregaRepository) {
        this.formaDeEntregaRepository = formaDeEntregaRepository;
    }

    /**
     * POST  /forma-de-entregas : Create a new formaDeEntrega.
     *
     * @param formaDeEntrega the formaDeEntrega to create
     * @return the ResponseEntity with status 201 (Created) and with body the new formaDeEntrega, or with status 400 (Bad Request) if the formaDeEntrega has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/forma-de-entregas")
    @Timed
    public ResponseEntity<FormaDeEntrega> createFormaDeEntrega(@RequestBody FormaDeEntrega formaDeEntrega) throws URISyntaxException {
        log.debug("REST request to save FormaDeEntrega : {}", formaDeEntrega);
        if (formaDeEntrega.getId() != null) {
            throw new BadRequestAlertException("A new formaDeEntrega cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FormaDeEntrega result = formaDeEntregaRepository.save(formaDeEntrega);
        return ResponseEntity.created(new URI("/api/forma-de-entregas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /forma-de-entregas : Updates an existing formaDeEntrega.
     *
     * @param formaDeEntrega the formaDeEntrega to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated formaDeEntrega,
     * or with status 400 (Bad Request) if the formaDeEntrega is not valid,
     * or with status 500 (Internal Server Error) if the formaDeEntrega couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/forma-de-entregas")
    @Timed
    public ResponseEntity<FormaDeEntrega> updateFormaDeEntrega(@RequestBody FormaDeEntrega formaDeEntrega) throws URISyntaxException {
        log.debug("REST request to update FormaDeEntrega : {}", formaDeEntrega);
        if (formaDeEntrega.getId() == null) {
            return createFormaDeEntrega(formaDeEntrega);
        }
        FormaDeEntrega result = formaDeEntregaRepository.save(formaDeEntrega);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, formaDeEntrega.getId().toString()))
            .body(result);
    }

    /**
     * GET  /forma-de-entregas : get all the formaDeEntregas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of formaDeEntregas in body
     */
    @GetMapping("/forma-de-entregas")
    @Timed
    public List<FormaDeEntrega> getAllFormaDeEntregas() {
        log.debug("REST request to get all FormaDeEntregas");
        return formaDeEntregaRepository.findAll();
        }

    /**
     * GET  /forma-de-entregas/:id : get the "id" formaDeEntrega.
     *
     * @param id the id of the formaDeEntrega to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the formaDeEntrega, or with status 404 (Not Found)
     */
    @GetMapping("/forma-de-entregas/{id}")
    @Timed
    public ResponseEntity<FormaDeEntrega> getFormaDeEntrega(@PathVariable Long id) {
        log.debug("REST request to get FormaDeEntrega : {}", id);
        FormaDeEntrega formaDeEntrega = formaDeEntregaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(formaDeEntrega));
    }

    /**
     * DELETE  /forma-de-entregas/:id : delete the "id" formaDeEntrega.
     *
     * @param id the id of the formaDeEntrega to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/forma-de-entregas/{id}")
    @Timed
    public ResponseEntity<Void> deleteFormaDeEntrega(@PathVariable Long id) {
        log.debug("REST request to delete FormaDeEntrega : {}", id);
        formaDeEntregaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

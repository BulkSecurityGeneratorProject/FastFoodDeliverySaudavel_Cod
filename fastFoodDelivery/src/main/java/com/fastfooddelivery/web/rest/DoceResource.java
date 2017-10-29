package com.fastfooddelivery.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fastfooddelivery.domain.Doce;

import com.fastfooddelivery.repository.DoceRepository;
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
 * REST controller for managing Doce.
 */
@RestController
@RequestMapping("/api")
public class DoceResource {

    private final Logger log = LoggerFactory.getLogger(DoceResource.class);

    private static final String ENTITY_NAME = "doce";

    private final DoceRepository doceRepository;

    public DoceResource(DoceRepository doceRepository) {
        this.doceRepository = doceRepository;
    }

    /**
     * POST  /doces : Create a new doce.
     *
     * @param doce the doce to create
     * @return the ResponseEntity with status 201 (Created) and with body the new doce, or with status 400 (Bad Request) if the doce has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/doces")
    @Timed
    public ResponseEntity<Doce> createDoce(@RequestBody Doce doce) throws URISyntaxException {
        log.debug("REST request to save Doce : {}", doce);
        if (doce.getId() != null) {
            throw new BadRequestAlertException("A new doce cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Doce result = doceRepository.save(doce);
        return ResponseEntity.created(new URI("/api/doces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /doces : Updates an existing doce.
     *
     * @param doce the doce to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated doce,
     * or with status 400 (Bad Request) if the doce is not valid,
     * or with status 500 (Internal Server Error) if the doce couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/doces")
    @Timed
    public ResponseEntity<Doce> updateDoce(@RequestBody Doce doce) throws URISyntaxException {
        log.debug("REST request to update Doce : {}", doce);
        if (doce.getId() == null) {
            return createDoce(doce);
        }
        Doce result = doceRepository.save(doce);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, doce.getId().toString()))
            .body(result);
    }

    /**
     * GET  /doces : get all the doces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of doces in body
     */
    @GetMapping("/doces")
    @Timed
    public ResponseEntity<List<Doce>> getAllDoces(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Doces");
        Page<Doce> page = doceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/doces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /doces/:id : get the "id" doce.
     *
     * @param id the id of the doce to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the doce, or with status 404 (Not Found)
     */
    @GetMapping("/doces/{id}")
    @Timed
    public ResponseEntity<Doce> getDoce(@PathVariable Long id) {
        log.debug("REST request to get Doce : {}", id);
        Doce doce = doceRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(doce));
    }

    /**
     * DELETE  /doces/:id : delete the "id" doce.
     *
     * @param id the id of the doce to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/doces/{id}")
    @Timed
    public ResponseEntity<Void> deleteDoce(@PathVariable Long id) {
        log.debug("REST request to delete Doce : {}", id);
        doceRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

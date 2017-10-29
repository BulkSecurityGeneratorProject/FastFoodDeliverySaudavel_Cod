package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.ValorNutricional;
import com.fastfooddelivery.repository.ValorNutricionalRepository;
import com.fastfooddelivery.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValorNutricionalResource REST controller.
 *
 * @see ValorNutricionalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class ValorNutricionalResourceIntTest {

    private static final Integer DEFAULT_CALORIA = 1;
    private static final Integer UPDATED_CALORIA = 2;

    private static final Integer DEFAULT_PROTEINA = 1;
    private static final Integer UPDATED_PROTEINA = 2;

    private static final Integer DEFAULT_CARBOIDRATO = 1;
    private static final Integer UPDATED_CARBOIDRATO = 2;

    private static final Integer DEFAULT_ACUCAR = 1;
    private static final Integer UPDATED_ACUCAR = 2;

    private static final Integer DEFAULT_GORDURAS_SATURADAS = 1;
    private static final Integer UPDATED_GORDURAS_SATURADAS = 2;

    private static final Integer DEFAULT_GORDURAS_TOTAIS = 1;
    private static final Integer UPDATED_GORDURAS_TOTAIS = 2;

    private static final Integer DEFAULT_SODIO = 1;
    private static final Integer UPDATED_SODIO = 2;

    @Autowired
    private ValorNutricionalRepository valorNutricionalRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValorNutricionalMockMvc;

    private ValorNutricional valorNutricional;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValorNutricionalResource valorNutricionalResource = new ValorNutricionalResource(valorNutricionalRepository);
        this.restValorNutricionalMockMvc = MockMvcBuilders.standaloneSetup(valorNutricionalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ValorNutricional createEntity(EntityManager em) {
        ValorNutricional valorNutricional = new ValorNutricional()
            .caloria(DEFAULT_CALORIA)
            .proteina(DEFAULT_PROTEINA)
            .carboidrato(DEFAULT_CARBOIDRATO)
            .acucar(DEFAULT_ACUCAR)
            .gordurasSaturadas(DEFAULT_GORDURAS_SATURADAS)
            .gordurasTotais(DEFAULT_GORDURAS_TOTAIS)
            .sodio(DEFAULT_SODIO);
        return valorNutricional;
    }

    @Before
    public void initTest() {
        valorNutricional = createEntity(em);
    }

    @Test
    @Transactional
    public void createValorNutricional() throws Exception {
        int databaseSizeBeforeCreate = valorNutricionalRepository.findAll().size();

        // Create the ValorNutricional
        restValorNutricionalMockMvc.perform(post("/api/valor-nutricionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorNutricional)))
            .andExpect(status().isCreated());

        // Validate the ValorNutricional in the database
        List<ValorNutricional> valorNutricionalList = valorNutricionalRepository.findAll();
        assertThat(valorNutricionalList).hasSize(databaseSizeBeforeCreate + 1);
        ValorNutricional testValorNutricional = valorNutricionalList.get(valorNutricionalList.size() - 1);
        assertThat(testValorNutricional.getCaloria()).isEqualTo(DEFAULT_CALORIA);
        assertThat(testValorNutricional.getProteina()).isEqualTo(DEFAULT_PROTEINA);
        assertThat(testValorNutricional.getCarboidrato()).isEqualTo(DEFAULT_CARBOIDRATO);
        assertThat(testValorNutricional.getAcucar()).isEqualTo(DEFAULT_ACUCAR);
        assertThat(testValorNutricional.getGordurasSaturadas()).isEqualTo(DEFAULT_GORDURAS_SATURADAS);
        assertThat(testValorNutricional.getGordurasTotais()).isEqualTo(DEFAULT_GORDURAS_TOTAIS);
        assertThat(testValorNutricional.getSodio()).isEqualTo(DEFAULT_SODIO);
    }

    @Test
    @Transactional
    public void createValorNutricionalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valorNutricionalRepository.findAll().size();

        // Create the ValorNutricional with an existing ID
        valorNutricional.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValorNutricionalMockMvc.perform(post("/api/valor-nutricionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorNutricional)))
            .andExpect(status().isBadRequest());

        // Validate the ValorNutricional in the database
        List<ValorNutricional> valorNutricionalList = valorNutricionalRepository.findAll();
        assertThat(valorNutricionalList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValorNutricionals() throws Exception {
        // Initialize the database
        valorNutricionalRepository.saveAndFlush(valorNutricional);

        // Get all the valorNutricionalList
        restValorNutricionalMockMvc.perform(get("/api/valor-nutricionals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valorNutricional.getId().intValue())))
            .andExpect(jsonPath("$.[*].caloria").value(hasItem(DEFAULT_CALORIA)))
            .andExpect(jsonPath("$.[*].proteina").value(hasItem(DEFAULT_PROTEINA)))
            .andExpect(jsonPath("$.[*].carboidrato").value(hasItem(DEFAULT_CARBOIDRATO)))
            .andExpect(jsonPath("$.[*].acucar").value(hasItem(DEFAULT_ACUCAR)))
            .andExpect(jsonPath("$.[*].gordurasSaturadas").value(hasItem(DEFAULT_GORDURAS_SATURADAS)))
            .andExpect(jsonPath("$.[*].gordurasTotais").value(hasItem(DEFAULT_GORDURAS_TOTAIS)))
            .andExpect(jsonPath("$.[*].sodio").value(hasItem(DEFAULT_SODIO)));
    }

    @Test
    @Transactional
    public void getValorNutricional() throws Exception {
        // Initialize the database
        valorNutricionalRepository.saveAndFlush(valorNutricional);

        // Get the valorNutricional
        restValorNutricionalMockMvc.perform(get("/api/valor-nutricionals/{id}", valorNutricional.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valorNutricional.getId().intValue()))
            .andExpect(jsonPath("$.caloria").value(DEFAULT_CALORIA))
            .andExpect(jsonPath("$.proteina").value(DEFAULT_PROTEINA))
            .andExpect(jsonPath("$.carboidrato").value(DEFAULT_CARBOIDRATO))
            .andExpect(jsonPath("$.acucar").value(DEFAULT_ACUCAR))
            .andExpect(jsonPath("$.gordurasSaturadas").value(DEFAULT_GORDURAS_SATURADAS))
            .andExpect(jsonPath("$.gordurasTotais").value(DEFAULT_GORDURAS_TOTAIS))
            .andExpect(jsonPath("$.sodio").value(DEFAULT_SODIO));
    }

    @Test
    @Transactional
    public void getNonExistingValorNutricional() throws Exception {
        // Get the valorNutricional
        restValorNutricionalMockMvc.perform(get("/api/valor-nutricionals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValorNutricional() throws Exception {
        // Initialize the database
        valorNutricionalRepository.saveAndFlush(valorNutricional);
        int databaseSizeBeforeUpdate = valorNutricionalRepository.findAll().size();

        // Update the valorNutricional
        ValorNutricional updatedValorNutricional = valorNutricionalRepository.findOne(valorNutricional.getId());
        updatedValorNutricional
            .caloria(UPDATED_CALORIA)
            .proteina(UPDATED_PROTEINA)
            .carboidrato(UPDATED_CARBOIDRATO)
            .acucar(UPDATED_ACUCAR)
            .gordurasSaturadas(UPDATED_GORDURAS_SATURADAS)
            .gordurasTotais(UPDATED_GORDURAS_TOTAIS)
            .sodio(UPDATED_SODIO);

        restValorNutricionalMockMvc.perform(put("/api/valor-nutricionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValorNutricional)))
            .andExpect(status().isOk());

        // Validate the ValorNutricional in the database
        List<ValorNutricional> valorNutricionalList = valorNutricionalRepository.findAll();
        assertThat(valorNutricionalList).hasSize(databaseSizeBeforeUpdate);
        ValorNutricional testValorNutricional = valorNutricionalList.get(valorNutricionalList.size() - 1);
        assertThat(testValorNutricional.getCaloria()).isEqualTo(UPDATED_CALORIA);
        assertThat(testValorNutricional.getProteina()).isEqualTo(UPDATED_PROTEINA);
        assertThat(testValorNutricional.getCarboidrato()).isEqualTo(UPDATED_CARBOIDRATO);
        assertThat(testValorNutricional.getAcucar()).isEqualTo(UPDATED_ACUCAR);
        assertThat(testValorNutricional.getGordurasSaturadas()).isEqualTo(UPDATED_GORDURAS_SATURADAS);
        assertThat(testValorNutricional.getGordurasTotais()).isEqualTo(UPDATED_GORDURAS_TOTAIS);
        assertThat(testValorNutricional.getSodio()).isEqualTo(UPDATED_SODIO);
    }

    @Test
    @Transactional
    public void updateNonExistingValorNutricional() throws Exception {
        int databaseSizeBeforeUpdate = valorNutricionalRepository.findAll().size();

        // Create the ValorNutricional

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValorNutricionalMockMvc.perform(put("/api/valor-nutricionals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorNutricional)))
            .andExpect(status().isCreated());

        // Validate the ValorNutricional in the database
        List<ValorNutricional> valorNutricionalList = valorNutricionalRepository.findAll();
        assertThat(valorNutricionalList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValorNutricional() throws Exception {
        // Initialize the database
        valorNutricionalRepository.saveAndFlush(valorNutricional);
        int databaseSizeBeforeDelete = valorNutricionalRepository.findAll().size();

        // Get the valorNutricional
        restValorNutricionalMockMvc.perform(delete("/api/valor-nutricionals/{id}", valorNutricional.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValorNutricional> valorNutricionalList = valorNutricionalRepository.findAll();
        assertThat(valorNutricionalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValorNutricional.class);
        ValorNutricional valorNutricional1 = new ValorNutricional();
        valorNutricional1.setId(1L);
        ValorNutricional valorNutricional2 = new ValorNutricional();
        valorNutricional2.setId(valorNutricional1.getId());
        assertThat(valorNutricional1).isEqualTo(valorNutricional2);
        valorNutricional2.setId(2L);
        assertThat(valorNutricional1).isNotEqualTo(valorNutricional2);
        valorNutricional1.setId(null);
        assertThat(valorNutricional1).isNotEqualTo(valorNutricional2);
    }
}

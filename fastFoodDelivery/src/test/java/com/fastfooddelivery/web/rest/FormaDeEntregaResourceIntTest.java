package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.FormaDeEntrega;
import com.fastfooddelivery.repository.FormaDeEntregaRepository;
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
 * Test class for the FormaDeEntregaResource REST controller.
 *
 * @see FormaDeEntregaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class FormaDeEntregaResourceIntTest {

    private static final Integer DEFAULT_FORMA_DE_ENTREGA = 1;
    private static final Integer UPDATED_FORMA_DE_ENTREGA = 2;

    @Autowired
    private FormaDeEntregaRepository formaDeEntregaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFormaDeEntregaMockMvc;

    private FormaDeEntrega formaDeEntrega;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FormaDeEntregaResource formaDeEntregaResource = new FormaDeEntregaResource(formaDeEntregaRepository);
        this.restFormaDeEntregaMockMvc = MockMvcBuilders.standaloneSetup(formaDeEntregaResource)
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
    public static FormaDeEntrega createEntity(EntityManager em) {
        FormaDeEntrega formaDeEntrega = new FormaDeEntrega()
            .formaDeEntrega(DEFAULT_FORMA_DE_ENTREGA);
        return formaDeEntrega;
    }

    @Before
    public void initTest() {
        formaDeEntrega = createEntity(em);
    }

    @Test
    @Transactional
    public void createFormaDeEntrega() throws Exception {
        int databaseSizeBeforeCreate = formaDeEntregaRepository.findAll().size();

        // Create the FormaDeEntrega
        restFormaDeEntregaMockMvc.perform(post("/api/forma-de-entregas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeEntrega)))
            .andExpect(status().isCreated());

        // Validate the FormaDeEntrega in the database
        List<FormaDeEntrega> formaDeEntregaList = formaDeEntregaRepository.findAll();
        assertThat(formaDeEntregaList).hasSize(databaseSizeBeforeCreate + 1);
        FormaDeEntrega testFormaDeEntrega = formaDeEntregaList.get(formaDeEntregaList.size() - 1);
        assertThat(testFormaDeEntrega.getFormaDeEntrega()).isEqualTo(DEFAULT_FORMA_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void createFormaDeEntregaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = formaDeEntregaRepository.findAll().size();

        // Create the FormaDeEntrega with an existing ID
        formaDeEntrega.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFormaDeEntregaMockMvc.perform(post("/api/forma-de-entregas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeEntrega)))
            .andExpect(status().isBadRequest());

        // Validate the FormaDeEntrega in the database
        List<FormaDeEntrega> formaDeEntregaList = formaDeEntregaRepository.findAll();
        assertThat(formaDeEntregaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFormaDeEntregas() throws Exception {
        // Initialize the database
        formaDeEntregaRepository.saveAndFlush(formaDeEntrega);

        // Get all the formaDeEntregaList
        restFormaDeEntregaMockMvc.perform(get("/api/forma-de-entregas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(formaDeEntrega.getId().intValue())))
            .andExpect(jsonPath("$.[*].formaDeEntrega").value(hasItem(DEFAULT_FORMA_DE_ENTREGA)));
    }

    @Test
    @Transactional
    public void getFormaDeEntrega() throws Exception {
        // Initialize the database
        formaDeEntregaRepository.saveAndFlush(formaDeEntrega);

        // Get the formaDeEntrega
        restFormaDeEntregaMockMvc.perform(get("/api/forma-de-entregas/{id}", formaDeEntrega.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(formaDeEntrega.getId().intValue()))
            .andExpect(jsonPath("$.formaDeEntrega").value(DEFAULT_FORMA_DE_ENTREGA));
    }

    @Test
    @Transactional
    public void getNonExistingFormaDeEntrega() throws Exception {
        // Get the formaDeEntrega
        restFormaDeEntregaMockMvc.perform(get("/api/forma-de-entregas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFormaDeEntrega() throws Exception {
        // Initialize the database
        formaDeEntregaRepository.saveAndFlush(formaDeEntrega);
        int databaseSizeBeforeUpdate = formaDeEntregaRepository.findAll().size();

        // Update the formaDeEntrega
        FormaDeEntrega updatedFormaDeEntrega = formaDeEntregaRepository.findOne(formaDeEntrega.getId());
        updatedFormaDeEntrega
            .formaDeEntrega(UPDATED_FORMA_DE_ENTREGA);

        restFormaDeEntregaMockMvc.perform(put("/api/forma-de-entregas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFormaDeEntrega)))
            .andExpect(status().isOk());

        // Validate the FormaDeEntrega in the database
        List<FormaDeEntrega> formaDeEntregaList = formaDeEntregaRepository.findAll();
        assertThat(formaDeEntregaList).hasSize(databaseSizeBeforeUpdate);
        FormaDeEntrega testFormaDeEntrega = formaDeEntregaList.get(formaDeEntregaList.size() - 1);
        assertThat(testFormaDeEntrega.getFormaDeEntrega()).isEqualTo(UPDATED_FORMA_DE_ENTREGA);
    }

    @Test
    @Transactional
    public void updateNonExistingFormaDeEntrega() throws Exception {
        int databaseSizeBeforeUpdate = formaDeEntregaRepository.findAll().size();

        // Create the FormaDeEntrega

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFormaDeEntregaMockMvc.perform(put("/api/forma-de-entregas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(formaDeEntrega)))
            .andExpect(status().isCreated());

        // Validate the FormaDeEntrega in the database
        List<FormaDeEntrega> formaDeEntregaList = formaDeEntregaRepository.findAll();
        assertThat(formaDeEntregaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFormaDeEntrega() throws Exception {
        // Initialize the database
        formaDeEntregaRepository.saveAndFlush(formaDeEntrega);
        int databaseSizeBeforeDelete = formaDeEntregaRepository.findAll().size();

        // Get the formaDeEntrega
        restFormaDeEntregaMockMvc.perform(delete("/api/forma-de-entregas/{id}", formaDeEntrega.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<FormaDeEntrega> formaDeEntregaList = formaDeEntregaRepository.findAll();
        assertThat(formaDeEntregaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FormaDeEntrega.class);
        FormaDeEntrega formaDeEntrega1 = new FormaDeEntrega();
        formaDeEntrega1.setId(1L);
        FormaDeEntrega formaDeEntrega2 = new FormaDeEntrega();
        formaDeEntrega2.setId(formaDeEntrega1.getId());
        assertThat(formaDeEntrega1).isEqualTo(formaDeEntrega2);
        formaDeEntrega2.setId(2L);
        assertThat(formaDeEntrega1).isNotEqualTo(formaDeEntrega2);
        formaDeEntrega1.setId(null);
        assertThat(formaDeEntrega1).isNotEqualTo(formaDeEntrega2);
    }
}

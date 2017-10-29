package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Bebida;
import com.fastfooddelivery.repository.BebidaRepository;
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
 * Test class for the BebidaResource REST controller.
 *
 * @see BebidaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class BebidaResourceIntTest {

    private static final String DEFAULT_BEBIDA = "AAAAAAAAAA";
    private static final String UPDATED_BEBIDA = "BBBBBBBBBB";

    @Autowired
    private BebidaRepository bebidaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restBebidaMockMvc;

    private Bebida bebida;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final BebidaResource bebidaResource = new BebidaResource(bebidaRepository);
        this.restBebidaMockMvc = MockMvcBuilders.standaloneSetup(bebidaResource)
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
    public static Bebida createEntity(EntityManager em) {
        Bebida bebida = new Bebida()
            .bebida(DEFAULT_BEBIDA);
        return bebida;
    }

    @Before
    public void initTest() {
        bebida = createEntity(em);
    }

    @Test
    @Transactional
    public void createBebida() throws Exception {
        int databaseSizeBeforeCreate = bebidaRepository.findAll().size();

        // Create the Bebida
        restBebidaMockMvc.perform(post("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebida)))
            .andExpect(status().isCreated());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeCreate + 1);
        Bebida testBebida = bebidaList.get(bebidaList.size() - 1);
        assertThat(testBebida.getBebida()).isEqualTo(DEFAULT_BEBIDA);
    }

    @Test
    @Transactional
    public void createBebidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bebidaRepository.findAll().size();

        // Create the Bebida with an existing ID
        bebida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBebidaMockMvc.perform(post("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebida)))
            .andExpect(status().isBadRequest());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllBebidas() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        // Get all the bebidaList
        restBebidaMockMvc.perform(get("/api/bebidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(bebida.getId().intValue())))
            .andExpect(jsonPath("$.[*].bebida").value(hasItem(DEFAULT_BEBIDA.toString())));
    }

    @Test
    @Transactional
    public void getBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);

        // Get the bebida
        restBebidaMockMvc.perform(get("/api/bebidas/{id}", bebida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(bebida.getId().intValue()))
            .andExpect(jsonPath("$.bebida").value(DEFAULT_BEBIDA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingBebida() throws Exception {
        // Get the bebida
        restBebidaMockMvc.perform(get("/api/bebidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);
        int databaseSizeBeforeUpdate = bebidaRepository.findAll().size();

        // Update the bebida
        Bebida updatedBebida = bebidaRepository.findOne(bebida.getId());
        updatedBebida
            .bebida(UPDATED_BEBIDA);

        restBebidaMockMvc.perform(put("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedBebida)))
            .andExpect(status().isOk());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeUpdate);
        Bebida testBebida = bebidaList.get(bebidaList.size() - 1);
        assertThat(testBebida.getBebida()).isEqualTo(UPDATED_BEBIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingBebida() throws Exception {
        int databaseSizeBeforeUpdate = bebidaRepository.findAll().size();

        // Create the Bebida

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restBebidaMockMvc.perform(put("/api/bebidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(bebida)))
            .andExpect(status().isCreated());

        // Validate the Bebida in the database
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteBebida() throws Exception {
        // Initialize the database
        bebidaRepository.saveAndFlush(bebida);
        int databaseSizeBeforeDelete = bebidaRepository.findAll().size();

        // Get the bebida
        restBebidaMockMvc.perform(delete("/api/bebidas/{id}", bebida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Bebida> bebidaList = bebidaRepository.findAll();
        assertThat(bebidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bebida.class);
        Bebida bebida1 = new Bebida();
        bebida1.setId(1L);
        Bebida bebida2 = new Bebida();
        bebida2.setId(bebida1.getId());
        assertThat(bebida1).isEqualTo(bebida2);
        bebida2.setId(2L);
        assertThat(bebida1).isNotEqualTo(bebida2);
        bebida1.setId(null);
        assertThat(bebida1).isNotEqualTo(bebida2);
    }
}

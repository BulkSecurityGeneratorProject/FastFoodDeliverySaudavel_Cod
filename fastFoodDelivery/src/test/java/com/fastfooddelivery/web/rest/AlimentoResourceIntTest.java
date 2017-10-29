package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Alimento;
import com.fastfooddelivery.repository.AlimentoRepository;
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
 * Test class for the AlimentoResource REST controller.
 *
 * @see AlimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class AlimentoResourceIntTest {

    private static final String DEFAULT_ALIMENTO_COL = "AAAAAAAAAA";
    private static final String UPDATED_ALIMENTO_COL = "BBBBBBBBBB";

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlimentoMockMvc;

    private Alimento alimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlimentoResource alimentoResource = new AlimentoResource(alimentoRepository);
        this.restAlimentoMockMvc = MockMvcBuilders.standaloneSetup(alimentoResource)
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
    public static Alimento createEntity(EntityManager em) {
        Alimento alimento = new Alimento()
            .alimentoCol(DEFAULT_ALIMENTO_COL);
        return alimento;
    }

    @Before
    public void initTest() {
        alimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlimento() throws Exception {
        int databaseSizeBeforeCreate = alimentoRepository.findAll().size();

        // Create the Alimento
        restAlimentoMockMvc.perform(post("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimento)))
            .andExpect(status().isCreated());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeCreate + 1);
        Alimento testAlimento = alimentoList.get(alimentoList.size() - 1);
        assertThat(testAlimento.getAlimentoCol()).isEqualTo(DEFAULT_ALIMENTO_COL);
    }

    @Test
    @Transactional
    public void createAlimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = alimentoRepository.findAll().size();

        // Create the Alimento with an existing ID
        alimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlimentoMockMvc.perform(post("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimento)))
            .andExpect(status().isBadRequest());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlimentos() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        // Get all the alimentoList
        restAlimentoMockMvc.perform(get("/api/alimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(alimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].alimentoCol").value(hasItem(DEFAULT_ALIMENTO_COL.toString())));
    }

    @Test
    @Transactional
    public void getAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);

        // Get the alimento
        restAlimentoMockMvc.perform(get("/api/alimentos/{id}", alimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(alimento.getId().intValue()))
            .andExpect(jsonPath("$.alimentoCol").value(DEFAULT_ALIMENTO_COL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlimento() throws Exception {
        // Get the alimento
        restAlimentoMockMvc.perform(get("/api/alimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);
        int databaseSizeBeforeUpdate = alimentoRepository.findAll().size();

        // Update the alimento
        Alimento updatedAlimento = alimentoRepository.findOne(alimento.getId());
        updatedAlimento
            .alimentoCol(UPDATED_ALIMENTO_COL);

        restAlimentoMockMvc.perform(put("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAlimento)))
            .andExpect(status().isOk());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeUpdate);
        Alimento testAlimento = alimentoList.get(alimentoList.size() - 1);
        assertThat(testAlimento.getAlimentoCol()).isEqualTo(UPDATED_ALIMENTO_COL);
    }

    @Test
    @Transactional
    public void updateNonExistingAlimento() throws Exception {
        int databaseSizeBeforeUpdate = alimentoRepository.findAll().size();

        // Create the Alimento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlimentoMockMvc.perform(put("/api/alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(alimento)))
            .andExpect(status().isCreated());

        // Validate the Alimento in the database
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlimento() throws Exception {
        // Initialize the database
        alimentoRepository.saveAndFlush(alimento);
        int databaseSizeBeforeDelete = alimentoRepository.findAll().size();

        // Get the alimento
        restAlimentoMockMvc.perform(delete("/api/alimentos/{id}", alimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Alimento> alimentoList = alimentoRepository.findAll();
        assertThat(alimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Alimento.class);
        Alimento alimento1 = new Alimento();
        alimento1.setId(1L);
        Alimento alimento2 = new Alimento();
        alimento2.setId(alimento1.getId());
        assertThat(alimento1).isEqualTo(alimento2);
        alimento2.setId(2L);
        assertThat(alimento1).isNotEqualTo(alimento2);
        alimento1.setId(null);
        assertThat(alimento1).isNotEqualTo(alimento2);
    }
}

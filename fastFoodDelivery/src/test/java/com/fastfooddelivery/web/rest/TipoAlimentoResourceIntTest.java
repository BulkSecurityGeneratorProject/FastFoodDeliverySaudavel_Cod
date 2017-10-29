package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.TipoAlimento;
import com.fastfooddelivery.repository.TipoAlimentoRepository;
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
 * Test class for the TipoAlimentoResource REST controller.
 *
 * @see TipoAlimentoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class TipoAlimentoResourceIntTest {

    private static final String DEFAULT_TIPO_ALIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO_ALIMENTO = "BBBBBBBBBB";

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoAlimentoMockMvc;

    private TipoAlimento tipoAlimento;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoAlimentoResource tipoAlimentoResource = new TipoAlimentoResource(tipoAlimentoRepository);
        this.restTipoAlimentoMockMvc = MockMvcBuilders.standaloneSetup(tipoAlimentoResource)
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
    public static TipoAlimento createEntity(EntityManager em) {
        TipoAlimento tipoAlimento = new TipoAlimento()
            .tipoAlimento(DEFAULT_TIPO_ALIMENTO);
        return tipoAlimento;
    }

    @Before
    public void initTest() {
        tipoAlimento = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoAlimento() throws Exception {
        int databaseSizeBeforeCreate = tipoAlimentoRepository.findAll().size();

        // Create the TipoAlimento
        restTipoAlimentoMockMvc.perform(post("/api/tipo-alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlimento)))
            .andExpect(status().isCreated());

        // Validate the TipoAlimento in the database
        List<TipoAlimento> tipoAlimentoList = tipoAlimentoRepository.findAll();
        assertThat(tipoAlimentoList).hasSize(databaseSizeBeforeCreate + 1);
        TipoAlimento testTipoAlimento = tipoAlimentoList.get(tipoAlimentoList.size() - 1);
        assertThat(testTipoAlimento.getTipoAlimento()).isEqualTo(DEFAULT_TIPO_ALIMENTO);
    }

    @Test
    @Transactional
    public void createTipoAlimentoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoAlimentoRepository.findAll().size();

        // Create the TipoAlimento with an existing ID
        tipoAlimento.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoAlimentoMockMvc.perform(post("/api/tipo-alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlimento)))
            .andExpect(status().isBadRequest());

        // Validate the TipoAlimento in the database
        List<TipoAlimento> tipoAlimentoList = tipoAlimentoRepository.findAll();
        assertThat(tipoAlimentoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoAlimentos() throws Exception {
        // Initialize the database
        tipoAlimentoRepository.saveAndFlush(tipoAlimento);

        // Get all the tipoAlimentoList
        restTipoAlimentoMockMvc.perform(get("/api/tipo-alimentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoAlimento.getId().intValue())))
            .andExpect(jsonPath("$.[*].tipoAlimento").value(hasItem(DEFAULT_TIPO_ALIMENTO.toString())));
    }

    @Test
    @Transactional
    public void getTipoAlimento() throws Exception {
        // Initialize the database
        tipoAlimentoRepository.saveAndFlush(tipoAlimento);

        // Get the tipoAlimento
        restTipoAlimentoMockMvc.perform(get("/api/tipo-alimentos/{id}", tipoAlimento.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoAlimento.getId().intValue()))
            .andExpect(jsonPath("$.tipoAlimento").value(DEFAULT_TIPO_ALIMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoAlimento() throws Exception {
        // Get the tipoAlimento
        restTipoAlimentoMockMvc.perform(get("/api/tipo-alimentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoAlimento() throws Exception {
        // Initialize the database
        tipoAlimentoRepository.saveAndFlush(tipoAlimento);
        int databaseSizeBeforeUpdate = tipoAlimentoRepository.findAll().size();

        // Update the tipoAlimento
        TipoAlimento updatedTipoAlimento = tipoAlimentoRepository.findOne(tipoAlimento.getId());
        updatedTipoAlimento
            .tipoAlimento(UPDATED_TIPO_ALIMENTO);

        restTipoAlimentoMockMvc.perform(put("/api/tipo-alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoAlimento)))
            .andExpect(status().isOk());

        // Validate the TipoAlimento in the database
        List<TipoAlimento> tipoAlimentoList = tipoAlimentoRepository.findAll();
        assertThat(tipoAlimentoList).hasSize(databaseSizeBeforeUpdate);
        TipoAlimento testTipoAlimento = tipoAlimentoList.get(tipoAlimentoList.size() - 1);
        assertThat(testTipoAlimento.getTipoAlimento()).isEqualTo(UPDATED_TIPO_ALIMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoAlimento() throws Exception {
        int databaseSizeBeforeUpdate = tipoAlimentoRepository.findAll().size();

        // Create the TipoAlimento

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoAlimentoMockMvc.perform(put("/api/tipo-alimentos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoAlimento)))
            .andExpect(status().isCreated());

        // Validate the TipoAlimento in the database
        List<TipoAlimento> tipoAlimentoList = tipoAlimentoRepository.findAll();
        assertThat(tipoAlimentoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoAlimento() throws Exception {
        // Initialize the database
        tipoAlimentoRepository.saveAndFlush(tipoAlimento);
        int databaseSizeBeforeDelete = tipoAlimentoRepository.findAll().size();

        // Get the tipoAlimento
        restTipoAlimentoMockMvc.perform(delete("/api/tipo-alimentos/{id}", tipoAlimento.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoAlimento> tipoAlimentoList = tipoAlimentoRepository.findAll();
        assertThat(tipoAlimentoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoAlimento.class);
        TipoAlimento tipoAlimento1 = new TipoAlimento();
        tipoAlimento1.setId(1L);
        TipoAlimento tipoAlimento2 = new TipoAlimento();
        tipoAlimento2.setId(tipoAlimento1.getId());
        assertThat(tipoAlimento1).isEqualTo(tipoAlimento2);
        tipoAlimento2.setId(2L);
        assertThat(tipoAlimento1).isNotEqualTo(tipoAlimento2);
        tipoAlimento1.setId(null);
        assertThat(tipoAlimento1).isNotEqualTo(tipoAlimento2);
    }
}

package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Preparo;
import com.fastfooddelivery.repository.PreparoRepository;
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
 * Test class for the PreparoResource REST controller.
 *
 * @see PreparoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class PreparoResourceIntTest {

    private static final String DEFAULT_PREPARO = "AAAAAAAAAA";
    private static final String UPDATED_PREPARO = "BBBBBBBBBB";

    @Autowired
    private PreparoRepository preparoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreparoMockMvc;

    private Preparo preparo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreparoResource preparoResource = new PreparoResource(preparoRepository);
        this.restPreparoMockMvc = MockMvcBuilders.standaloneSetup(preparoResource)
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
    public static Preparo createEntity(EntityManager em) {
        Preparo preparo = new Preparo()
            .preparo(DEFAULT_PREPARO);
        return preparo;
    }

    @Before
    public void initTest() {
        preparo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreparo() throws Exception {
        int databaseSizeBeforeCreate = preparoRepository.findAll().size();

        // Create the Preparo
        restPreparoMockMvc.perform(post("/api/preparos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preparo)))
            .andExpect(status().isCreated());

        // Validate the Preparo in the database
        List<Preparo> preparoList = preparoRepository.findAll();
        assertThat(preparoList).hasSize(databaseSizeBeforeCreate + 1);
        Preparo testPreparo = preparoList.get(preparoList.size() - 1);
        assertThat(testPreparo.getPreparo()).isEqualTo(DEFAULT_PREPARO);
    }

    @Test
    @Transactional
    public void createPreparoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preparoRepository.findAll().size();

        // Create the Preparo with an existing ID
        preparo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreparoMockMvc.perform(post("/api/preparos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preparo)))
            .andExpect(status().isBadRequest());

        // Validate the Preparo in the database
        List<Preparo> preparoList = preparoRepository.findAll();
        assertThat(preparoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreparos() throws Exception {
        // Initialize the database
        preparoRepository.saveAndFlush(preparo);

        // Get all the preparoList
        restPreparoMockMvc.perform(get("/api/preparos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preparo.getId().intValue())))
            .andExpect(jsonPath("$.[*].preparo").value(hasItem(DEFAULT_PREPARO.toString())));
    }

    @Test
    @Transactional
    public void getPreparo() throws Exception {
        // Initialize the database
        preparoRepository.saveAndFlush(preparo);

        // Get the preparo
        restPreparoMockMvc.perform(get("/api/preparos/{id}", preparo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preparo.getId().intValue()))
            .andExpect(jsonPath("$.preparo").value(DEFAULT_PREPARO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreparo() throws Exception {
        // Get the preparo
        restPreparoMockMvc.perform(get("/api/preparos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreparo() throws Exception {
        // Initialize the database
        preparoRepository.saveAndFlush(preparo);
        int databaseSizeBeforeUpdate = preparoRepository.findAll().size();

        // Update the preparo
        Preparo updatedPreparo = preparoRepository.findOne(preparo.getId());
        updatedPreparo
            .preparo(UPDATED_PREPARO);

        restPreparoMockMvc.perform(put("/api/preparos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPreparo)))
            .andExpect(status().isOk());

        // Validate the Preparo in the database
        List<Preparo> preparoList = preparoRepository.findAll();
        assertThat(preparoList).hasSize(databaseSizeBeforeUpdate);
        Preparo testPreparo = preparoList.get(preparoList.size() - 1);
        assertThat(testPreparo.getPreparo()).isEqualTo(UPDATED_PREPARO);
    }

    @Test
    @Transactional
    public void updateNonExistingPreparo() throws Exception {
        int databaseSizeBeforeUpdate = preparoRepository.findAll().size();

        // Create the Preparo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreparoMockMvc.perform(put("/api/preparos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preparo)))
            .andExpect(status().isCreated());

        // Validate the Preparo in the database
        List<Preparo> preparoList = preparoRepository.findAll();
        assertThat(preparoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreparo() throws Exception {
        // Initialize the database
        preparoRepository.saveAndFlush(preparo);
        int databaseSizeBeforeDelete = preparoRepository.findAll().size();

        // Get the preparo
        restPreparoMockMvc.perform(delete("/api/preparos/{id}", preparo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Preparo> preparoList = preparoRepository.findAll();
        assertThat(preparoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Preparo.class);
        Preparo preparo1 = new Preparo();
        preparo1.setId(1L);
        Preparo preparo2 = new Preparo();
        preparo2.setId(preparo1.getId());
        assertThat(preparo1).isEqualTo(preparo2);
        preparo2.setId(2L);
        assertThat(preparo1).isNotEqualTo(preparo2);
        preparo1.setId(null);
        assertThat(preparo1).isNotEqualTo(preparo2);
    }
}

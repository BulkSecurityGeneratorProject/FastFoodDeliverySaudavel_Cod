package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Tempero;
import com.fastfooddelivery.repository.TemperoRepository;
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
 * Test class for the TemperoResource REST controller.
 *
 * @see TemperoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class TemperoResourceIntTest {

    private static final String DEFAULT_TEMPERO = "AAAAAAAAAA";
    private static final String UPDATED_TEMPERO = "BBBBBBBBBB";

    @Autowired
    private TemperoRepository temperoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTemperoMockMvc;

    private Tempero tempero;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TemperoResource temperoResource = new TemperoResource(temperoRepository);
        this.restTemperoMockMvc = MockMvcBuilders.standaloneSetup(temperoResource)
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
    public static Tempero createEntity(EntityManager em) {
        Tempero tempero = new Tempero()
            .tempero(DEFAULT_TEMPERO);
        return tempero;
    }

    @Before
    public void initTest() {
        tempero = createEntity(em);
    }

    @Test
    @Transactional
    public void createTempero() throws Exception {
        int databaseSizeBeforeCreate = temperoRepository.findAll().size();

        // Create the Tempero
        restTemperoMockMvc.perform(post("/api/temperos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tempero)))
            .andExpect(status().isCreated());

        // Validate the Tempero in the database
        List<Tempero> temperoList = temperoRepository.findAll();
        assertThat(temperoList).hasSize(databaseSizeBeforeCreate + 1);
        Tempero testTempero = temperoList.get(temperoList.size() - 1);
        assertThat(testTempero.getTempero()).isEqualTo(DEFAULT_TEMPERO);
    }

    @Test
    @Transactional
    public void createTemperoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = temperoRepository.findAll().size();

        // Create the Tempero with an existing ID
        tempero.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTemperoMockMvc.perform(post("/api/temperos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tempero)))
            .andExpect(status().isBadRequest());

        // Validate the Tempero in the database
        List<Tempero> temperoList = temperoRepository.findAll();
        assertThat(temperoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTemperos() throws Exception {
        // Initialize the database
        temperoRepository.saveAndFlush(tempero);

        // Get all the temperoList
        restTemperoMockMvc.perform(get("/api/temperos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tempero.getId().intValue())))
            .andExpect(jsonPath("$.[*].tempero").value(hasItem(DEFAULT_TEMPERO.toString())));
    }

    @Test
    @Transactional
    public void getTempero() throws Exception {
        // Initialize the database
        temperoRepository.saveAndFlush(tempero);

        // Get the tempero
        restTemperoMockMvc.perform(get("/api/temperos/{id}", tempero.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tempero.getId().intValue()))
            .andExpect(jsonPath("$.tempero").value(DEFAULT_TEMPERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTempero() throws Exception {
        // Get the tempero
        restTemperoMockMvc.perform(get("/api/temperos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTempero() throws Exception {
        // Initialize the database
        temperoRepository.saveAndFlush(tempero);
        int databaseSizeBeforeUpdate = temperoRepository.findAll().size();

        // Update the tempero
        Tempero updatedTempero = temperoRepository.findOne(tempero.getId());
        updatedTempero
            .tempero(UPDATED_TEMPERO);

        restTemperoMockMvc.perform(put("/api/temperos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTempero)))
            .andExpect(status().isOk());

        // Validate the Tempero in the database
        List<Tempero> temperoList = temperoRepository.findAll();
        assertThat(temperoList).hasSize(databaseSizeBeforeUpdate);
        Tempero testTempero = temperoList.get(temperoList.size() - 1);
        assertThat(testTempero.getTempero()).isEqualTo(UPDATED_TEMPERO);
    }

    @Test
    @Transactional
    public void updateNonExistingTempero() throws Exception {
        int databaseSizeBeforeUpdate = temperoRepository.findAll().size();

        // Create the Tempero

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTemperoMockMvc.perform(put("/api/temperos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tempero)))
            .andExpect(status().isCreated());

        // Validate the Tempero in the database
        List<Tempero> temperoList = temperoRepository.findAll();
        assertThat(temperoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTempero() throws Exception {
        // Initialize the database
        temperoRepository.saveAndFlush(tempero);
        int databaseSizeBeforeDelete = temperoRepository.findAll().size();

        // Get the tempero
        restTemperoMockMvc.perform(delete("/api/temperos/{id}", tempero.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Tempero> temperoList = temperoRepository.findAll();
        assertThat(temperoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tempero.class);
        Tempero tempero1 = new Tempero();
        tempero1.setId(1L);
        Tempero tempero2 = new Tempero();
        tempero2.setId(tempero1.getId());
        assertThat(tempero1).isEqualTo(tempero2);
        tempero2.setId(2L);
        assertThat(tempero1).isNotEqualTo(tempero2);
        tempero1.setId(null);
        assertThat(tempero1).isNotEqualTo(tempero2);
    }
}

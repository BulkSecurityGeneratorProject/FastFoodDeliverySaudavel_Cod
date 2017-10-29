package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Doce;
import com.fastfooddelivery.repository.DoceRepository;
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
 * Test class for the DoceResource REST controller.
 *
 * @see DoceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class DoceResourceIntTest {

    private static final String DEFAULT_DOCE = "AAAAAAAAAA";
    private static final String UPDATED_DOCE = "BBBBBBBBBB";

    @Autowired
    private DoceRepository doceRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDoceMockMvc;

    private Doce doce;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DoceResource doceResource = new DoceResource(doceRepository);
        this.restDoceMockMvc = MockMvcBuilders.standaloneSetup(doceResource)
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
    public static Doce createEntity(EntityManager em) {
        Doce doce = new Doce()
            .doce(DEFAULT_DOCE);
        return doce;
    }

    @Before
    public void initTest() {
        doce = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoce() throws Exception {
        int databaseSizeBeforeCreate = doceRepository.findAll().size();

        // Create the Doce
        restDoceMockMvc.perform(post("/api/doces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doce)))
            .andExpect(status().isCreated());

        // Validate the Doce in the database
        List<Doce> doceList = doceRepository.findAll();
        assertThat(doceList).hasSize(databaseSizeBeforeCreate + 1);
        Doce testDoce = doceList.get(doceList.size() - 1);
        assertThat(testDoce.getDoce()).isEqualTo(DEFAULT_DOCE);
    }

    @Test
    @Transactional
    public void createDoceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doceRepository.findAll().size();

        // Create the Doce with an existing ID
        doce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoceMockMvc.perform(post("/api/doces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doce)))
            .andExpect(status().isBadRequest());

        // Validate the Doce in the database
        List<Doce> doceList = doceRepository.findAll();
        assertThat(doceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDoces() throws Exception {
        // Initialize the database
        doceRepository.saveAndFlush(doce);

        // Get all the doceList
        restDoceMockMvc.perform(get("/api/doces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doce.getId().intValue())))
            .andExpect(jsonPath("$.[*].doce").value(hasItem(DEFAULT_DOCE.toString())));
    }

    @Test
    @Transactional
    public void getDoce() throws Exception {
        // Initialize the database
        doceRepository.saveAndFlush(doce);

        // Get the doce
        restDoceMockMvc.perform(get("/api/doces/{id}", doce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(doce.getId().intValue()))
            .andExpect(jsonPath("$.doce").value(DEFAULT_DOCE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDoce() throws Exception {
        // Get the doce
        restDoceMockMvc.perform(get("/api/doces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoce() throws Exception {
        // Initialize the database
        doceRepository.saveAndFlush(doce);
        int databaseSizeBeforeUpdate = doceRepository.findAll().size();

        // Update the doce
        Doce updatedDoce = doceRepository.findOne(doce.getId());
        updatedDoce
            .doce(UPDATED_DOCE);

        restDoceMockMvc.perform(put("/api/doces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoce)))
            .andExpect(status().isOk());

        // Validate the Doce in the database
        List<Doce> doceList = doceRepository.findAll();
        assertThat(doceList).hasSize(databaseSizeBeforeUpdate);
        Doce testDoce = doceList.get(doceList.size() - 1);
        assertThat(testDoce.getDoce()).isEqualTo(UPDATED_DOCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDoce() throws Exception {
        int databaseSizeBeforeUpdate = doceRepository.findAll().size();

        // Create the Doce

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDoceMockMvc.perform(put("/api/doces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(doce)))
            .andExpect(status().isCreated());

        // Validate the Doce in the database
        List<Doce> doceList = doceRepository.findAll();
        assertThat(doceList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDoce() throws Exception {
        // Initialize the database
        doceRepository.saveAndFlush(doce);
        int databaseSizeBeforeDelete = doceRepository.findAll().size();

        // Get the doce
        restDoceMockMvc.perform(delete("/api/doces/{id}", doce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Doce> doceList = doceRepository.findAll();
        assertThat(doceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doce.class);
        Doce doce1 = new Doce();
        doce1.setId(1L);
        Doce doce2 = new Doce();
        doce2.setId(doce1.getId());
        assertThat(doce1).isEqualTo(doce2);
        doce2.setId(2L);
        assertThat(doce1).isNotEqualTo(doce2);
        doce1.setId(null);
        assertThat(doce1).isNotEqualTo(doce2);
    }
}

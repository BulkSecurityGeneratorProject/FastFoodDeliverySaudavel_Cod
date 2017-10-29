package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Refeicao;
import com.fastfooddelivery.repository.RefeicaoRepository;
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
 * Test class for the RefeicaoResource REST controller.
 *
 * @see RefeicaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class RefeicaoResourceIntTest {

    private static final String DEFAULT_REFEICAO = "AAAAAAAAAA";
    private static final String UPDATED_REFEICAO = "BBBBBBBBBB";

    @Autowired
    private RefeicaoRepository refeicaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRefeicaoMockMvc;

    private Refeicao refeicao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RefeicaoResource refeicaoResource = new RefeicaoResource(refeicaoRepository);
        this.restRefeicaoMockMvc = MockMvcBuilders.standaloneSetup(refeicaoResource)
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
    public static Refeicao createEntity(EntityManager em) {
        Refeicao refeicao = new Refeicao()
            .refeicao(DEFAULT_REFEICAO);
        return refeicao;
    }

    @Before
    public void initTest() {
        refeicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createRefeicao() throws Exception {
        int databaseSizeBeforeCreate = refeicaoRepository.findAll().size();

        // Create the Refeicao
        restRefeicaoMockMvc.perform(post("/api/refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refeicao)))
            .andExpect(status().isCreated());

        // Validate the Refeicao in the database
        List<Refeicao> refeicaoList = refeicaoRepository.findAll();
        assertThat(refeicaoList).hasSize(databaseSizeBeforeCreate + 1);
        Refeicao testRefeicao = refeicaoList.get(refeicaoList.size() - 1);
        assertThat(testRefeicao.getRefeicao()).isEqualTo(DEFAULT_REFEICAO);
    }

    @Test
    @Transactional
    public void createRefeicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = refeicaoRepository.findAll().size();

        // Create the Refeicao with an existing ID
        refeicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRefeicaoMockMvc.perform(post("/api/refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refeicao)))
            .andExpect(status().isBadRequest());

        // Validate the Refeicao in the database
        List<Refeicao> refeicaoList = refeicaoRepository.findAll();
        assertThat(refeicaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRefeicaos() throws Exception {
        // Initialize the database
        refeicaoRepository.saveAndFlush(refeicao);

        // Get all the refeicaoList
        restRefeicaoMockMvc.perform(get("/api/refeicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(refeicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].refeicao").value(hasItem(DEFAULT_REFEICAO.toString())));
    }

    @Test
    @Transactional
    public void getRefeicao() throws Exception {
        // Initialize the database
        refeicaoRepository.saveAndFlush(refeicao);

        // Get the refeicao
        restRefeicaoMockMvc.perform(get("/api/refeicaos/{id}", refeicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(refeicao.getId().intValue()))
            .andExpect(jsonPath("$.refeicao").value(DEFAULT_REFEICAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRefeicao() throws Exception {
        // Get the refeicao
        restRefeicaoMockMvc.perform(get("/api/refeicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRefeicao() throws Exception {
        // Initialize the database
        refeicaoRepository.saveAndFlush(refeicao);
        int databaseSizeBeforeUpdate = refeicaoRepository.findAll().size();

        // Update the refeicao
        Refeicao updatedRefeicao = refeicaoRepository.findOne(refeicao.getId());
        updatedRefeicao
            .refeicao(UPDATED_REFEICAO);

        restRefeicaoMockMvc.perform(put("/api/refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRefeicao)))
            .andExpect(status().isOk());

        // Validate the Refeicao in the database
        List<Refeicao> refeicaoList = refeicaoRepository.findAll();
        assertThat(refeicaoList).hasSize(databaseSizeBeforeUpdate);
        Refeicao testRefeicao = refeicaoList.get(refeicaoList.size() - 1);
        assertThat(testRefeicao.getRefeicao()).isEqualTo(UPDATED_REFEICAO);
    }

    @Test
    @Transactional
    public void updateNonExistingRefeicao() throws Exception {
        int databaseSizeBeforeUpdate = refeicaoRepository.findAll().size();

        // Create the Refeicao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRefeicaoMockMvc.perform(put("/api/refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(refeicao)))
            .andExpect(status().isCreated());

        // Validate the Refeicao in the database
        List<Refeicao> refeicaoList = refeicaoRepository.findAll();
        assertThat(refeicaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRefeicao() throws Exception {
        // Initialize the database
        refeicaoRepository.saveAndFlush(refeicao);
        int databaseSizeBeforeDelete = refeicaoRepository.findAll().size();

        // Get the refeicao
        restRefeicaoMockMvc.perform(delete("/api/refeicaos/{id}", refeicao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Refeicao> refeicaoList = refeicaoRepository.findAll();
        assertThat(refeicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Refeicao.class);
        Refeicao refeicao1 = new Refeicao();
        refeicao1.setId(1L);
        Refeicao refeicao2 = new Refeicao();
        refeicao2.setId(refeicao1.getId());
        assertThat(refeicao1).isEqualTo(refeicao2);
        refeicao2.setId(2L);
        assertThat(refeicao1).isNotEqualTo(refeicao2);
        refeicao1.setId(null);
        assertThat(refeicao1).isNotEqualTo(refeicao2);
    }
}

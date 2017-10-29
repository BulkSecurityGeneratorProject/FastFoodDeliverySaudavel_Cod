package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.ValorRefeicao;
import com.fastfooddelivery.repository.ValorRefeicaoRepository;
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
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ValorRefeicaoResource REST controller.
 *
 * @see ValorRefeicaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class ValorRefeicaoResourceIntTest {

    private static final BigDecimal DEFAULT_VALOR = new BigDecimal(1);
    private static final BigDecimal UPDATED_VALOR = new BigDecimal(2);

    @Autowired
    private ValorRefeicaoRepository valorRefeicaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restValorRefeicaoMockMvc;

    private ValorRefeicao valorRefeicao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ValorRefeicaoResource valorRefeicaoResource = new ValorRefeicaoResource(valorRefeicaoRepository);
        this.restValorRefeicaoMockMvc = MockMvcBuilders.standaloneSetup(valorRefeicaoResource)
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
    public static ValorRefeicao createEntity(EntityManager em) {
        ValorRefeicao valorRefeicao = new ValorRefeicao()
            .valor(DEFAULT_VALOR);
        return valorRefeicao;
    }

    @Before
    public void initTest() {
        valorRefeicao = createEntity(em);
    }

    @Test
    @Transactional
    public void createValorRefeicao() throws Exception {
        int databaseSizeBeforeCreate = valorRefeicaoRepository.findAll().size();

        // Create the ValorRefeicao
        restValorRefeicaoMockMvc.perform(post("/api/valor-refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorRefeicao)))
            .andExpect(status().isCreated());

        // Validate the ValorRefeicao in the database
        List<ValorRefeicao> valorRefeicaoList = valorRefeicaoRepository.findAll();
        assertThat(valorRefeicaoList).hasSize(databaseSizeBeforeCreate + 1);
        ValorRefeicao testValorRefeicao = valorRefeicaoList.get(valorRefeicaoList.size() - 1);
        assertThat(testValorRefeicao.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createValorRefeicaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = valorRefeicaoRepository.findAll().size();

        // Create the ValorRefeicao with an existing ID
        valorRefeicao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restValorRefeicaoMockMvc.perform(post("/api/valor-refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorRefeicao)))
            .andExpect(status().isBadRequest());

        // Validate the ValorRefeicao in the database
        List<ValorRefeicao> valorRefeicaoList = valorRefeicaoRepository.findAll();
        assertThat(valorRefeicaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllValorRefeicaos() throws Exception {
        // Initialize the database
        valorRefeicaoRepository.saveAndFlush(valorRefeicao);

        // Get all the valorRefeicaoList
        restValorRefeicaoMockMvc.perform(get("/api/valor-refeicaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(valorRefeicao.getId().intValue())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.intValue())));
    }

    @Test
    @Transactional
    public void getValorRefeicao() throws Exception {
        // Initialize the database
        valorRefeicaoRepository.saveAndFlush(valorRefeicao);

        // Get the valorRefeicao
        restValorRefeicaoMockMvc.perform(get("/api/valor-refeicaos/{id}", valorRefeicao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(valorRefeicao.getId().intValue()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingValorRefeicao() throws Exception {
        // Get the valorRefeicao
        restValorRefeicaoMockMvc.perform(get("/api/valor-refeicaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateValorRefeicao() throws Exception {
        // Initialize the database
        valorRefeicaoRepository.saveAndFlush(valorRefeicao);
        int databaseSizeBeforeUpdate = valorRefeicaoRepository.findAll().size();

        // Update the valorRefeicao
        ValorRefeicao updatedValorRefeicao = valorRefeicaoRepository.findOne(valorRefeicao.getId());
        updatedValorRefeicao
            .valor(UPDATED_VALOR);

        restValorRefeicaoMockMvc.perform(put("/api/valor-refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedValorRefeicao)))
            .andExpect(status().isOk());

        // Validate the ValorRefeicao in the database
        List<ValorRefeicao> valorRefeicaoList = valorRefeicaoRepository.findAll();
        assertThat(valorRefeicaoList).hasSize(databaseSizeBeforeUpdate);
        ValorRefeicao testValorRefeicao = valorRefeicaoList.get(valorRefeicaoList.size() - 1);
        assertThat(testValorRefeicao.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingValorRefeicao() throws Exception {
        int databaseSizeBeforeUpdate = valorRefeicaoRepository.findAll().size();

        // Create the ValorRefeicao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restValorRefeicaoMockMvc.perform(put("/api/valor-refeicaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(valorRefeicao)))
            .andExpect(status().isCreated());

        // Validate the ValorRefeicao in the database
        List<ValorRefeicao> valorRefeicaoList = valorRefeicaoRepository.findAll();
        assertThat(valorRefeicaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteValorRefeicao() throws Exception {
        // Initialize the database
        valorRefeicaoRepository.saveAndFlush(valorRefeicao);
        int databaseSizeBeforeDelete = valorRefeicaoRepository.findAll().size();

        // Get the valorRefeicao
        restValorRefeicaoMockMvc.perform(delete("/api/valor-refeicaos/{id}", valorRefeicao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ValorRefeicao> valorRefeicaoList = valorRefeicaoRepository.findAll();
        assertThat(valorRefeicaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ValorRefeicao.class);
        ValorRefeicao valorRefeicao1 = new ValorRefeicao();
        valorRefeicao1.setId(1L);
        ValorRefeicao valorRefeicao2 = new ValorRefeicao();
        valorRefeicao2.setId(valorRefeicao1.getId());
        assertThat(valorRefeicao1).isEqualTo(valorRefeicao2);
        valorRefeicao2.setId(2L);
        assertThat(valorRefeicao1).isNotEqualTo(valorRefeicao2);
        valorRefeicao1.setId(null);
        assertThat(valorRefeicao1).isNotEqualTo(valorRefeicao2);
    }
}

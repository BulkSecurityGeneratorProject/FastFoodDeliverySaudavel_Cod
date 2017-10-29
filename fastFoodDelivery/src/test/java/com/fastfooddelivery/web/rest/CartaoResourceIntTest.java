package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Cartao;
import com.fastfooddelivery.repository.CartaoRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CartaoResource REST controller.
 *
 * @see CartaoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class CartaoResourceIntTest {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final LocalDate DEFAULT_DATA_VENCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_VENCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CVV = 1;
    private static final Integer UPDATED_CVV = 2;

    private static final String DEFAULT_CARTAO_COL = "AAAAAAAAAA";
    private static final String UPDATED_CARTAO_COL = "BBBBBBBBBB";

    @Autowired
    private CartaoRepository cartaoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCartaoMockMvc;

    private Cartao cartao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartaoResource cartaoResource = new CartaoResource(cartaoRepository);
        this.restCartaoMockMvc = MockMvcBuilders.standaloneSetup(cartaoResource)
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
    public static Cartao createEntity(EntityManager em) {
        Cartao cartao = new Cartao()
            .numero(DEFAULT_NUMERO)
            .dataVencimento(DEFAULT_DATA_VENCIMENTO)
            .cvv(DEFAULT_CVV)
            .cartaoCol(DEFAULT_CARTAO_COL);
        return cartao;
    }

    @Before
    public void initTest() {
        cartao = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartao() throws Exception {
        int databaseSizeBeforeCreate = cartaoRepository.findAll().size();

        // Create the Cartao
        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartao)))
            .andExpect(status().isCreated());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeCreate + 1);
        Cartao testCartao = cartaoList.get(cartaoList.size() - 1);
        assertThat(testCartao.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCartao.getDataVencimento()).isEqualTo(DEFAULT_DATA_VENCIMENTO);
        assertThat(testCartao.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testCartao.getCartaoCol()).isEqualTo(DEFAULT_CARTAO_COL);
    }

    @Test
    @Transactional
    public void createCartaoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartaoRepository.findAll().size();

        // Create the Cartao with an existing ID
        cartao.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartaoMockMvc.perform(post("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartao)))
            .andExpect(status().isBadRequest());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCartaos() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        // Get all the cartaoList
        restCartaoMockMvc.perform(get("/api/cartaos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartao.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].dataVencimento").value(hasItem(DEFAULT_DATA_VENCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV)))
            .andExpect(jsonPath("$.[*].cartaoCol").value(hasItem(DEFAULT_CARTAO_COL.toString())));
    }

    @Test
    @Transactional
    public void getCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);

        // Get the cartao
        restCartaoMockMvc.perform(get("/api/cartaos/{id}", cartao.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartao.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.dataVencimento").value(DEFAULT_DATA_VENCIMENTO.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV))
            .andExpect(jsonPath("$.cartaoCol").value(DEFAULT_CARTAO_COL.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCartao() throws Exception {
        // Get the cartao
        restCartaoMockMvc.perform(get("/api/cartaos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);
        int databaseSizeBeforeUpdate = cartaoRepository.findAll().size();

        // Update the cartao
        Cartao updatedCartao = cartaoRepository.findOne(cartao.getId());
        updatedCartao
            .numero(UPDATED_NUMERO)
            .dataVencimento(UPDATED_DATA_VENCIMENTO)
            .cvv(UPDATED_CVV)
            .cartaoCol(UPDATED_CARTAO_COL);

        restCartaoMockMvc.perform(put("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartao)))
            .andExpect(status().isOk());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeUpdate);
        Cartao testCartao = cartaoList.get(cartaoList.size() - 1);
        assertThat(testCartao.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCartao.getDataVencimento()).isEqualTo(UPDATED_DATA_VENCIMENTO);
        assertThat(testCartao.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testCartao.getCartaoCol()).isEqualTo(UPDATED_CARTAO_COL);
    }

    @Test
    @Transactional
    public void updateNonExistingCartao() throws Exception {
        int databaseSizeBeforeUpdate = cartaoRepository.findAll().size();

        // Create the Cartao

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCartaoMockMvc.perform(put("/api/cartaos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartao)))
            .andExpect(status().isCreated());

        // Validate the Cartao in the database
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCartao() throws Exception {
        // Initialize the database
        cartaoRepository.saveAndFlush(cartao);
        int databaseSizeBeforeDelete = cartaoRepository.findAll().size();

        // Get the cartao
        restCartaoMockMvc.perform(delete("/api/cartaos/{id}", cartao.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cartao> cartaoList = cartaoRepository.findAll();
        assertThat(cartaoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cartao.class);
        Cartao cartao1 = new Cartao();
        cartao1.setId(1L);
        Cartao cartao2 = new Cartao();
        cartao2.setId(cartao1.getId());
        assertThat(cartao1).isEqualTo(cartao2);
        cartao2.setId(2L);
        assertThat(cartao1).isNotEqualTo(cartao2);
        cartao1.setId(null);
        assertThat(cartao1).isNotEqualTo(cartao2);
    }
}

package com.fastfooddelivery.web.rest;

import com.fastfooddelivery.FastFoodDeliveryApp;

import com.fastfooddelivery.domain.Pessoa;
import com.fastfooddelivery.repository.PessoaRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PessoaResource REST controller.
 *
 * @see PessoaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FastFoodDeliveryApp.class)
public class PessoaResourceIntTest {

    private static final String DEFAULT_CELULAR = "AAAAAAAAAA";
    private static final String UPDATED_CELULAR = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_SEXO = "AAAAAAAAAA";
    private static final String UPDATED_SEXO = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PESO = new BigDecimal(1);
    private static final BigDecimal UPDATED_PESO = new BigDecimal(2);

    private static final BigDecimal DEFAULT_ALTURA = new BigDecimal(1);
    private static final BigDecimal UPDATED_ALTURA = new BigDecimal(2);

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPessoaMockMvc;

    private Pessoa pessoa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PessoaResource pessoaResource = new PessoaResource(pessoaRepository);
        this.restPessoaMockMvc = MockMvcBuilders.standaloneSetup(pessoaResource)
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
    public static Pessoa createEntity(EntityManager em) {
        Pessoa pessoa = new Pessoa()
            .celular(DEFAULT_CELULAR)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .sexo(DEFAULT_SEXO)
            .peso(DEFAULT_PESO)
            .altura(DEFAULT_ALTURA);
        return pessoa;
    }

    @Before
    public void initTest() {
        pessoa = createEntity(em);
    }

    @Test
    @Transactional
    public void createPessoa() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoa)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate + 1);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getCelular()).isEqualTo(DEFAULT_CELULAR);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testPessoa.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testPessoa.getPeso()).isEqualTo(DEFAULT_PESO);
        assertThat(testPessoa.getAltura()).isEqualTo(DEFAULT_ALTURA);
    }

    @Test
    @Transactional
    public void createPessoaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pessoaRepository.findAll().size();

        // Create the Pessoa with an existing ID
        pessoa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPessoaMockMvc.perform(post("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoa)))
            .andExpect(status().isBadRequest());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPessoas() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get all the pessoaList
        restPessoaMockMvc.perform(get("/api/pessoas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pessoa.getId().intValue())))
            .andExpect(jsonPath("$.[*].celular").value(hasItem(DEFAULT_CELULAR)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].peso").value(hasItem(DEFAULT_PESO.intValue())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.intValue())));
    }

    @Test
    @Transactional
    public void getPessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);

        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", pessoa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(pessoa.getId().intValue()))
            .andExpect(jsonPath("$.celular").value(DEFAULT_CELULAR))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.peso").value(DEFAULT_PESO.intValue()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPessoa() throws Exception {
        // Get the pessoa
        restPessoaMockMvc.perform(get("/api/pessoas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Update the pessoa
        Pessoa updatedPessoa = pessoaRepository.findOne(pessoa.getId());
        updatedPessoa
            .celular(UPDATED_CELULAR)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .sexo(UPDATED_SEXO)
            .peso(UPDATED_PESO)
            .altura(UPDATED_ALTURA);

        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPessoa)))
            .andExpect(status().isOk());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate);
        Pessoa testPessoa = pessoaList.get(pessoaList.size() - 1);
        assertThat(testPessoa.getCelular()).isEqualTo(UPDATED_CELULAR);
        assertThat(testPessoa.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testPessoa.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testPessoa.getPeso()).isEqualTo(UPDATED_PESO);
        assertThat(testPessoa.getAltura()).isEqualTo(UPDATED_ALTURA);
    }

    @Test
    @Transactional
    public void updateNonExistingPessoa() throws Exception {
        int databaseSizeBeforeUpdate = pessoaRepository.findAll().size();

        // Create the Pessoa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPessoaMockMvc.perform(put("/api/pessoas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(pessoa)))
            .andExpect(status().isCreated());

        // Validate the Pessoa in the database
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePessoa() throws Exception {
        // Initialize the database
        pessoaRepository.saveAndFlush(pessoa);
        int databaseSizeBeforeDelete = pessoaRepository.findAll().size();

        // Get the pessoa
        restPessoaMockMvc.perform(delete("/api/pessoas/{id}", pessoa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Pessoa> pessoaList = pessoaRepository.findAll();
        assertThat(pessoaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pessoa.class);
        Pessoa pessoa1 = new Pessoa();
        pessoa1.setId(1L);
        Pessoa pessoa2 = new Pessoa();
        pessoa2.setId(pessoa1.getId());
        assertThat(pessoa1).isEqualTo(pessoa2);
        pessoa2.setId(2L);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
        pessoa1.setId(null);
        assertThat(pessoa1).isNotEqualTo(pessoa2);
    }
}

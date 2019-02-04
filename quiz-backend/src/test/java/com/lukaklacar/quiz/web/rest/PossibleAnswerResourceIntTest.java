package com.lukaklacar.quiz.web.rest;

import com.lukaklacar.quiz.QuizApp;

import com.lukaklacar.quiz.domain.PossibleAnswer;
import com.lukaklacar.quiz.repository.PossibleAnswerRepository;
import com.lukaklacar.quiz.service.PossibleAnswerService;
import com.lukaklacar.quiz.service.dto.PossibleAnswerDTO;
import com.lukaklacar.quiz.service.mapper.PossibleAnswerMapper;
import com.lukaklacar.quiz.web.rest.errors.ExceptionTranslator;

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
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.lukaklacar.quiz.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the PossibleAnswerResource REST controller.
 *
 * @see PossibleAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuizApp.class)
public class PossibleAnswerResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CORRECT = false;
    private static final Boolean UPDATED_CORRECT = true;

    @Autowired
    private PossibleAnswerRepository possibleAnswerRepository;

    @Autowired
    private PossibleAnswerMapper possibleAnswerMapper;

    @Autowired
    private PossibleAnswerService possibleAnswerService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPossibleAnswerMockMvc;

    private PossibleAnswer possibleAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PossibleAnswerResource possibleAnswerResource = new PossibleAnswerResource(possibleAnswerService);
        this.restPossibleAnswerMockMvc = MockMvcBuilders.standaloneSetup(possibleAnswerResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PossibleAnswer createEntity(EntityManager em) {
        PossibleAnswer possibleAnswer = new PossibleAnswer()
            .text(DEFAULT_TEXT)
            .correct(DEFAULT_CORRECT);
        return possibleAnswer;
    }

    @Before
    public void initTest() {
        possibleAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createPossibleAnswer() throws Exception {
        int databaseSizeBeforeCreate = possibleAnswerRepository.findAll().size();

        // Create the PossibleAnswer
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(possibleAnswer);
        restPossibleAnswerMockMvc.perform(post("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the PossibleAnswer in the database
        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        PossibleAnswer testPossibleAnswer = possibleAnswerList.get(possibleAnswerList.size() - 1);
        assertThat(testPossibleAnswer.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testPossibleAnswer.isCorrect()).isEqualTo(DEFAULT_CORRECT);
    }

    @Test
    @Transactional
    public void createPossibleAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = possibleAnswerRepository.findAll().size();

        // Create the PossibleAnswer with an existing ID
        possibleAnswer.setId(1L);
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(possibleAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPossibleAnswerMockMvc.perform(post("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PossibleAnswer in the database
        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTextIsRequired() throws Exception {
        int databaseSizeBeforeTest = possibleAnswerRepository.findAll().size();
        // set the field null
        possibleAnswer.setText(null);

        // Create the PossibleAnswer, which fails.
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(possibleAnswer);

        restPossibleAnswerMockMvc.perform(post("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCorrectIsRequired() throws Exception {
        int databaseSizeBeforeTest = possibleAnswerRepository.findAll().size();
        // set the field null
        possibleAnswer.setCorrect(null);

        // Create the PossibleAnswer, which fails.
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(possibleAnswer);

        restPossibleAnswerMockMvc.perform(post("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isBadRequest());

        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPossibleAnswers() throws Exception {
        // Initialize the database
        possibleAnswerRepository.saveAndFlush(possibleAnswer);

        // Get all the possibleAnswerList
        restPossibleAnswerMockMvc.perform(get("/api/possible-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(possibleAnswer.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].correct").value(hasItem(DEFAULT_CORRECT.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPossibleAnswer() throws Exception {
        // Initialize the database
        possibleAnswerRepository.saveAndFlush(possibleAnswer);

        // Get the possibleAnswer
        restPossibleAnswerMockMvc.perform(get("/api/possible-answers/{id}", possibleAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(possibleAnswer.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.correct").value(DEFAULT_CORRECT.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPossibleAnswer() throws Exception {
        // Get the possibleAnswer
        restPossibleAnswerMockMvc.perform(get("/api/possible-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePossibleAnswer() throws Exception {
        // Initialize the database
        possibleAnswerRepository.saveAndFlush(possibleAnswer);

        int databaseSizeBeforeUpdate = possibleAnswerRepository.findAll().size();

        // Update the possibleAnswer
        PossibleAnswer updatedPossibleAnswer = possibleAnswerRepository.findById(possibleAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedPossibleAnswer are not directly saved in db
        em.detach(updatedPossibleAnswer);
        updatedPossibleAnswer
            .text(UPDATED_TEXT)
            .correct(UPDATED_CORRECT);
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(updatedPossibleAnswer);

        restPossibleAnswerMockMvc.perform(put("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the PossibleAnswer in the database
        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeUpdate);
        PossibleAnswer testPossibleAnswer = possibleAnswerList.get(possibleAnswerList.size() - 1);
        assertThat(testPossibleAnswer.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testPossibleAnswer.isCorrect()).isEqualTo(UPDATED_CORRECT);
    }

    @Test
    @Transactional
    public void updateNonExistingPossibleAnswer() throws Exception {
        int databaseSizeBeforeUpdate = possibleAnswerRepository.findAll().size();

        // Create the PossibleAnswer
        PossibleAnswerDTO possibleAnswerDTO = possibleAnswerMapper.toDto(possibleAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPossibleAnswerMockMvc.perform(put("/api/possible-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(possibleAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PossibleAnswer in the database
        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePossibleAnswer() throws Exception {
        // Initialize the database
        possibleAnswerRepository.saveAndFlush(possibleAnswer);

        int databaseSizeBeforeDelete = possibleAnswerRepository.findAll().size();

        // Delete the possibleAnswer
        restPossibleAnswerMockMvc.perform(delete("/api/possible-answers/{id}", possibleAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PossibleAnswer> possibleAnswerList = possibleAnswerRepository.findAll();
        assertThat(possibleAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossibleAnswer.class);
        PossibleAnswer possibleAnswer1 = new PossibleAnswer();
        possibleAnswer1.setId(1L);
        PossibleAnswer possibleAnswer2 = new PossibleAnswer();
        possibleAnswer2.setId(possibleAnswer1.getId());
        assertThat(possibleAnswer1).isEqualTo(possibleAnswer2);
        possibleAnswer2.setId(2L);
        assertThat(possibleAnswer1).isNotEqualTo(possibleAnswer2);
        possibleAnswer1.setId(null);
        assertThat(possibleAnswer1).isNotEqualTo(possibleAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PossibleAnswerDTO.class);
        PossibleAnswerDTO possibleAnswerDTO1 = new PossibleAnswerDTO();
        possibleAnswerDTO1.setId(1L);
        PossibleAnswerDTO possibleAnswerDTO2 = new PossibleAnswerDTO();
        assertThat(possibleAnswerDTO1).isNotEqualTo(possibleAnswerDTO2);
        possibleAnswerDTO2.setId(possibleAnswerDTO1.getId());
        assertThat(possibleAnswerDTO1).isEqualTo(possibleAnswerDTO2);
        possibleAnswerDTO2.setId(2L);
        assertThat(possibleAnswerDTO1).isNotEqualTo(possibleAnswerDTO2);
        possibleAnswerDTO1.setId(null);
        assertThat(possibleAnswerDTO1).isNotEqualTo(possibleAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(possibleAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(possibleAnswerMapper.fromId(null)).isNull();
    }
}

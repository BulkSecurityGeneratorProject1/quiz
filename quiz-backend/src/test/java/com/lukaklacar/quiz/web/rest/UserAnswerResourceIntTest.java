package com.lukaklacar.quiz.web.rest;

import com.lukaklacar.quiz.QuizApp;

import com.lukaklacar.quiz.domain.UserAnswer;
import com.lukaklacar.quiz.repository.UserAnswerRepository;
import com.lukaklacar.quiz.service.UserAnswerService;
import com.lukaklacar.quiz.service.dto.UserAnswerDTO;
import com.lukaklacar.quiz.service.mapper.UserAnswerMapper;
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
 * Test class for the UserAnswerResource REST controller.
 *
 * @see UserAnswerResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = QuizApp.class)
public class UserAnswerResourceIntTest {

    @Autowired
    private UserAnswerRepository userAnswerRepository;

    @Autowired
    private UserAnswerMapper userAnswerMapper;

    @Autowired
    private UserAnswerService userAnswerService;

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

    private MockMvc restUserAnswerMockMvc;

    private UserAnswer userAnswer;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserAnswerResource userAnswerResource = new UserAnswerResource(userAnswerService);
        this.restUserAnswerMockMvc = MockMvcBuilders.standaloneSetup(userAnswerResource)
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
    public static UserAnswer createEntity(EntityManager em) {
        UserAnswer userAnswer = new UserAnswer();
        return userAnswer;
    }

    @Before
    public void initTest() {
        userAnswer = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserAnswer() throws Exception {
        int databaseSizeBeforeCreate = userAnswerRepository.findAll().size();

        // Create the UserAnswer
        UserAnswerDTO userAnswerDTO = userAnswerMapper.toDto(userAnswer);
        restUserAnswerMockMvc.perform(post("/api/user-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnswerDTO)))
            .andExpect(status().isCreated());

        // Validate the UserAnswer in the database
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll();
        assertThat(userAnswerList).hasSize(databaseSizeBeforeCreate + 1);
        UserAnswer testUserAnswer = userAnswerList.get(userAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void createUserAnswerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userAnswerRepository.findAll().size();

        // Create the UserAnswer with an existing ID
        userAnswer.setId(1L);
        UserAnswerDTO userAnswerDTO = userAnswerMapper.toDto(userAnswer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserAnswerMockMvc.perform(post("/api/user-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAnswer in the database
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll();
        assertThat(userAnswerList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserAnswers() throws Exception {
        // Initialize the database
        userAnswerRepository.saveAndFlush(userAnswer);

        // Get all the userAnswerList
        restUserAnswerMockMvc.perform(get("/api/user-answers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userAnswer.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getUserAnswer() throws Exception {
        // Initialize the database
        userAnswerRepository.saveAndFlush(userAnswer);

        // Get the userAnswer
        restUserAnswerMockMvc.perform(get("/api/user-answers/{id}", userAnswer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userAnswer.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUserAnswer() throws Exception {
        // Get the userAnswer
        restUserAnswerMockMvc.perform(get("/api/user-answers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserAnswer() throws Exception {
        // Initialize the database
        userAnswerRepository.saveAndFlush(userAnswer);

        int databaseSizeBeforeUpdate = userAnswerRepository.findAll().size();

        // Update the userAnswer
        UserAnswer updatedUserAnswer = userAnswerRepository.findById(userAnswer.getId()).get();
        // Disconnect from session so that the updates on updatedUserAnswer are not directly saved in db
        em.detach(updatedUserAnswer);
        UserAnswerDTO userAnswerDTO = userAnswerMapper.toDto(updatedUserAnswer);

        restUserAnswerMockMvc.perform(put("/api/user-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnswerDTO)))
            .andExpect(status().isOk());

        // Validate the UserAnswer in the database
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll();
        assertThat(userAnswerList).hasSize(databaseSizeBeforeUpdate);
        UserAnswer testUserAnswer = userAnswerList.get(userAnswerList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingUserAnswer() throws Exception {
        int databaseSizeBeforeUpdate = userAnswerRepository.findAll().size();

        // Create the UserAnswer
        UserAnswerDTO userAnswerDTO = userAnswerMapper.toDto(userAnswer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserAnswerMockMvc.perform(put("/api/user-answers")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userAnswerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserAnswer in the database
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll();
        assertThat(userAnswerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserAnswer() throws Exception {
        // Initialize the database
        userAnswerRepository.saveAndFlush(userAnswer);

        int databaseSizeBeforeDelete = userAnswerRepository.findAll().size();

        // Delete the userAnswer
        restUserAnswerMockMvc.perform(delete("/api/user-answers/{id}", userAnswer.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserAnswer> userAnswerList = userAnswerRepository.findAll();
        assertThat(userAnswerList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnswer.class);
        UserAnswer userAnswer1 = new UserAnswer();
        userAnswer1.setId(1L);
        UserAnswer userAnswer2 = new UserAnswer();
        userAnswer2.setId(userAnswer1.getId());
        assertThat(userAnswer1).isEqualTo(userAnswer2);
        userAnswer2.setId(2L);
        assertThat(userAnswer1).isNotEqualTo(userAnswer2);
        userAnswer1.setId(null);
        assertThat(userAnswer1).isNotEqualTo(userAnswer2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserAnswerDTO.class);
        UserAnswerDTO userAnswerDTO1 = new UserAnswerDTO();
        userAnswerDTO1.setId(1L);
        UserAnswerDTO userAnswerDTO2 = new UserAnswerDTO();
        assertThat(userAnswerDTO1).isNotEqualTo(userAnswerDTO2);
        userAnswerDTO2.setId(userAnswerDTO1.getId());
        assertThat(userAnswerDTO1).isEqualTo(userAnswerDTO2);
        userAnswerDTO2.setId(2L);
        assertThat(userAnswerDTO1).isNotEqualTo(userAnswerDTO2);
        userAnswerDTO1.setId(null);
        assertThat(userAnswerDTO1).isNotEqualTo(userAnswerDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userAnswerMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userAnswerMapper.fromId(null)).isNull();
    }
}

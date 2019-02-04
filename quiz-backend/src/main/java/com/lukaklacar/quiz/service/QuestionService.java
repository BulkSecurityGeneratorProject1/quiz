package com.lukaklacar.quiz.service;

import com.lukaklacar.quiz.service.dto.QuestionDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Question.
 */
public interface QuestionService {

    /**
     * Save a question.
     *
     * @param questionDTO the entity to save
     * @return the persisted entity
     */
    QuestionDTO save(QuestionDTO questionDTO);

    /**
     * Get all the questions.
     *
     * @return the list of entities
     */
    List<QuestionDTO> findAll();


    /**
     * Get the "id" question.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<QuestionDTO> findOne(Long id);

    /**
     * Delete the "id" question.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

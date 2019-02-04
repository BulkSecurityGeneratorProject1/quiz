package com.lukaklacar.quiz.service;

import com.lukaklacar.quiz.service.dto.UserAnswerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing UserAnswer.
 */
public interface UserAnswerService {

    /**
     * Save a userAnswer.
     *
     * @param userAnswerDTO the entity to save
     * @return the persisted entity
     */
    UserAnswerDTO save(UserAnswerDTO userAnswerDTO);

    /**
     * Get all the userAnswers.
     *
     * @return the list of entities
     */
    List<UserAnswerDTO> findAll();


    /**
     * Get the "id" userAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UserAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" userAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

package com.lukaklacar.quiz.service;

import com.lukaklacar.quiz.service.dto.PossibleAnswerDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PossibleAnswer.
 */
public interface PossibleAnswerService {

    /**
     * Save a possibleAnswer.
     *
     * @param possibleAnswerDTO the entity to save
     * @return the persisted entity
     */
    PossibleAnswerDTO save(PossibleAnswerDTO possibleAnswerDTO);

    /**
     * Get all the possibleAnswers.
     *
     * @return the list of entities
     */
    List<PossibleAnswerDTO> findAll();


    /**
     * Get the "id" possibleAnswer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PossibleAnswerDTO> findOne(Long id);

    /**
     * Delete the "id" possibleAnswer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}

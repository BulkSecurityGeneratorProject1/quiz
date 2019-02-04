package com.lukaklacar.quiz.service.impl;

import com.lukaklacar.quiz.service.PossibleAnswerService;
import com.lukaklacar.quiz.domain.PossibleAnswer;
import com.lukaklacar.quiz.repository.PossibleAnswerRepository;
import com.lukaklacar.quiz.service.dto.PossibleAnswerDTO;
import com.lukaklacar.quiz.service.mapper.PossibleAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing PossibleAnswer.
 */
@Service
@Transactional
public class PossibleAnswerServiceImpl implements PossibleAnswerService {

    private final Logger log = LoggerFactory.getLogger(PossibleAnswerServiceImpl.class);

    private final PossibleAnswerRepository possibleAnswerRepository;

    private final PossibleAnswerMapper possibleAnswerMapper;

    public PossibleAnswerServiceImpl(PossibleAnswerRepository possibleAnswerRepository, PossibleAnswerMapper possibleAnswerMapper) {
        this.possibleAnswerRepository = possibleAnswerRepository;
        this.possibleAnswerMapper = possibleAnswerMapper;
    }

    /**
     * Save a possibleAnswer.
     *
     * @param possibleAnswerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PossibleAnswerDTO save(PossibleAnswerDTO possibleAnswerDTO) {
        log.debug("Request to save PossibleAnswer : {}", possibleAnswerDTO);
        PossibleAnswer possibleAnswer = possibleAnswerMapper.toEntity(possibleAnswerDTO);
        possibleAnswer = possibleAnswerRepository.save(possibleAnswer);
        return possibleAnswerMapper.toDto(possibleAnswer);
    }

    /**
     * Get all the possibleAnswers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PossibleAnswerDTO> findAll() {
        log.debug("Request to get all PossibleAnswers");
        return possibleAnswerRepository.findAll().stream()
            .map(possibleAnswerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one possibleAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PossibleAnswerDTO> findOne(Long id) {
        log.debug("Request to get PossibleAnswer : {}", id);
        return possibleAnswerRepository.findById(id)
            .map(possibleAnswerMapper::toDto);
    }

    /**
     * Delete the possibleAnswer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PossibleAnswer : {}", id);        possibleAnswerRepository.deleteById(id);
    }
}

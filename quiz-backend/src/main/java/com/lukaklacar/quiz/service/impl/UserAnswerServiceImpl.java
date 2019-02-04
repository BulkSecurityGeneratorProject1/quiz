package com.lukaklacar.quiz.service.impl;

import com.lukaklacar.quiz.service.UserAnswerService;
import com.lukaklacar.quiz.domain.UserAnswer;
import com.lukaklacar.quiz.repository.UserAnswerRepository;
import com.lukaklacar.quiz.service.dto.UserAnswerDTO;
import com.lukaklacar.quiz.service.mapper.UserAnswerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing UserAnswer.
 */
@Service
@Transactional
public class UserAnswerServiceImpl implements UserAnswerService {

    private final Logger log = LoggerFactory.getLogger(UserAnswerServiceImpl.class);

    private final UserAnswerRepository userAnswerRepository;

    private final UserAnswerMapper userAnswerMapper;

    public UserAnswerServiceImpl(UserAnswerRepository userAnswerRepository, UserAnswerMapper userAnswerMapper) {
        this.userAnswerRepository = userAnswerRepository;
        this.userAnswerMapper = userAnswerMapper;
    }

    /**
     * Save a userAnswer.
     *
     * @param userAnswerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAnswerDTO save(UserAnswerDTO userAnswerDTO) {
        log.debug("Request to save UserAnswer : {}", userAnswerDTO);
        UserAnswer userAnswer = userAnswerMapper.toEntity(userAnswerDTO);
        userAnswer = userAnswerRepository.save(userAnswer);
        return userAnswerMapper.toDto(userAnswer);
    }

    /**
     * Get all the userAnswers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<UserAnswerDTO> findAll() {
        log.debug("Request to get all UserAnswers");
        return userAnswerRepository.findAll().stream()
            .map(userAnswerMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one userAnswer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UserAnswerDTO> findOne(Long id) {
        log.debug("Request to get UserAnswer : {}", id);
        return userAnswerRepository.findById(id)
            .map(userAnswerMapper::toDto);
    }

    /**
     * Delete the userAnswer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAnswer : {}", id);        userAnswerRepository.deleteById(id);
    }
}

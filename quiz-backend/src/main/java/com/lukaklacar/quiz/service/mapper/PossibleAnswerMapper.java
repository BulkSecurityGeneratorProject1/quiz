package com.lukaklacar.quiz.service.mapper;

import com.lukaklacar.quiz.domain.*;
import com.lukaklacar.quiz.service.dto.PossibleAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PossibleAnswer and its DTO PossibleAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class})
public interface PossibleAnswerMapper extends EntityMapper<PossibleAnswerDTO, PossibleAnswer> {

    @Mapping(source = "question.id", target = "questionId")
    PossibleAnswerDTO toDto(PossibleAnswer possibleAnswer);

    @Mapping(source = "questionId", target = "question")
    PossibleAnswer toEntity(PossibleAnswerDTO possibleAnswerDTO);

    default PossibleAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        PossibleAnswer possibleAnswer = new PossibleAnswer();
        possibleAnswer.setId(id);
        return possibleAnswer;
    }
}

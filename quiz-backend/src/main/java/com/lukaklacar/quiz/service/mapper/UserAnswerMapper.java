package com.lukaklacar.quiz.service.mapper;

import com.lukaklacar.quiz.domain.*;
import com.lukaklacar.quiz.service.dto.UserAnswerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAnswer and its DTO UserAnswerDTO.
 */
@Mapper(componentModel = "spring", uses = {QuestionMapper.class, PossibleAnswerMapper.class, UserMapper.class})
public interface UserAnswerMapper extends EntityMapper<UserAnswerDTO, UserAnswer> {

    @Mapping(source = "question.id", target = "questionId")
    @Mapping(source = "answer.id", target = "answerId")
    @Mapping(source = "user.id", target = "userId")
    UserAnswerDTO toDto(UserAnswer userAnswer);

    @Mapping(source = "questionId", target = "question")
    @Mapping(source = "answerId", target = "answer")
    @Mapping(source = "userId", target = "user")
    UserAnswer toEntity(UserAnswerDTO userAnswerDTO);

    default UserAnswer fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAnswer userAnswer = new UserAnswer();
        userAnswer.setId(id);
        return userAnswer;
    }
}

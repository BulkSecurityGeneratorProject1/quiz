package com.lukaklacar.quiz.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UserAnswer entity.
 */
public class UserAnswerDTO implements Serializable {

    private Long id;


    private Long questionId;

    private Long answerId;

    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Long getAnswerId() {
        return answerId;
    }

    public void setAnswerId(Long possibleAnswerId) {
        this.answerId = possibleAnswerId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAnswerDTO userAnswerDTO = (UserAnswerDTO) o;
        if (userAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAnswerDTO{" +
            "id=" + getId() +
            ", question=" + getQuestionId() +
            ", answer=" + getAnswerId() +
            ", user=" + getUserId() +
            "}";
    }
}

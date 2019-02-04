package com.lukaklacar.quiz.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the PossibleAnswer entity.
 */
public class PossibleAnswerDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(min = 3)
    private String text;

    @NotNull
    private Boolean correct;


    private Long questionId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PossibleAnswerDTO possibleAnswerDTO = (PossibleAnswerDTO) o;
        if (possibleAnswerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), possibleAnswerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PossibleAnswerDTO{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", correct='" + isCorrect() + "'" +
            ", question=" + getQuestionId() +
            "}";
    }
}

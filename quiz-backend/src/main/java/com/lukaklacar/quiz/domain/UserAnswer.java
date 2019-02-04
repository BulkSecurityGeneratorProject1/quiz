package com.lukaklacar.quiz.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A UserAnswer.
 */
@Entity
@Table(name = "user_answer")
public class UserAnswer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(unique = true)
    private Question question;

    @OneToOne
    @JoinColumn(unique = true)
    private PossibleAnswer answer;

    @ManyToOne
    @JsonIgnoreProperties("userAnswers")
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public UserAnswer question(Question question) {
        this.question = question;
        return this;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public PossibleAnswer getAnswer() {
        return answer;
    }

    public UserAnswer answer(PossibleAnswer possibleAnswer) {
        this.answer = possibleAnswer;
        return this;
    }

    public void setAnswer(PossibleAnswer possibleAnswer) {
        this.answer = possibleAnswer;
    }

    public User getUser() {
        return user;
    }

    public UserAnswer user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UserAnswer userAnswer = (UserAnswer) o;
        if (userAnswer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
            "id=" + getId() +
            "}";
    }
}

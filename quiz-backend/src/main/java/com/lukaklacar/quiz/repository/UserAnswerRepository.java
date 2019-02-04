package com.lukaklacar.quiz.repository;

import com.lukaklacar.quiz.domain.UserAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {

    @Query("select user_answer from UserAnswer user_answer where user_answer.user.login = ?#{principal.username}")
    List<UserAnswer> findByUserIsCurrentUser();

}

package com.lukaklacar.quiz.repository;

import com.lukaklacar.quiz.domain.PossibleAnswer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PossibleAnswer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PossibleAnswerRepository extends JpaRepository<PossibleAnswer, Long> {

}

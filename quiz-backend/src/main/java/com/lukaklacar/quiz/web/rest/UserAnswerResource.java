package com.lukaklacar.quiz.web.rest;
import com.lukaklacar.quiz.service.UserAnswerService;
import com.lukaklacar.quiz.web.rest.errors.BadRequestAlertException;
import com.lukaklacar.quiz.web.rest.util.HeaderUtil;
import com.lukaklacar.quiz.service.dto.UserAnswerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing UserAnswer.
 */
@RestController
@RequestMapping("/api")
public class UserAnswerResource {

    private final Logger log = LoggerFactory.getLogger(UserAnswerResource.class);

    private static final String ENTITY_NAME = "userAnswer";

    private final UserAnswerService userAnswerService;

    public UserAnswerResource(UserAnswerService userAnswerService) {
        this.userAnswerService = userAnswerService;
    }

    /**
     * POST  /user-answers : Create a new userAnswer.
     *
     * @param userAnswerDTO the userAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userAnswerDTO, or with status 400 (Bad Request) if the userAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-answers")
    public ResponseEntity<UserAnswerDTO> createUserAnswer(@RequestBody UserAnswerDTO userAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save UserAnswer : {}", userAnswerDTO);
        if (userAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new userAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserAnswerDTO result = userAnswerService.save(userAnswerDTO);
        return ResponseEntity.created(new URI("/api/user-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-answers : Updates an existing userAnswer.
     *
     * @param userAnswerDTO the userAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userAnswerDTO,
     * or with status 400 (Bad Request) if the userAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the userAnswerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-answers")
    public ResponseEntity<UserAnswerDTO> updateUserAnswer(@RequestBody UserAnswerDTO userAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update UserAnswer : {}", userAnswerDTO);
        if (userAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserAnswerDTO result = userAnswerService.save(userAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-answers : get all the userAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of userAnswers in body
     */
    @GetMapping("/user-answers")
    public List<UserAnswerDTO> getAllUserAnswers() {
        log.debug("REST request to get all UserAnswers");
        return userAnswerService.findAll();
    }

    /**
     * GET  /user-answers/:id : get the "id" userAnswer.
     *
     * @param id the id of the userAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userAnswerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-answers/{id}")
    public ResponseEntity<UserAnswerDTO> getUserAnswer(@PathVariable Long id) {
        log.debug("REST request to get UserAnswer : {}", id);
        Optional<UserAnswerDTO> userAnswerDTO = userAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userAnswerDTO);
    }

    /**
     * DELETE  /user-answers/:id : delete the "id" userAnswer.
     *
     * @param id the id of the userAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-answers/{id}")
    public ResponseEntity<Void> deleteUserAnswer(@PathVariable Long id) {
        log.debug("REST request to delete UserAnswer : {}", id);
        userAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

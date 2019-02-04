package com.lukaklacar.quiz.web.rest;
import com.lukaklacar.quiz.service.PossibleAnswerService;
import com.lukaklacar.quiz.web.rest.errors.BadRequestAlertException;
import com.lukaklacar.quiz.web.rest.util.HeaderUtil;
import com.lukaklacar.quiz.service.dto.PossibleAnswerDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PossibleAnswer.
 */
@RestController
@RequestMapping("/api")
public class PossibleAnswerResource {

    private final Logger log = LoggerFactory.getLogger(PossibleAnswerResource.class);

    private static final String ENTITY_NAME = "possibleAnswer";

    private final PossibleAnswerService possibleAnswerService;

    public PossibleAnswerResource(PossibleAnswerService possibleAnswerService) {
        this.possibleAnswerService = possibleAnswerService;
    }

    /**
     * POST  /possible-answers : Create a new possibleAnswer.
     *
     * @param possibleAnswerDTO the possibleAnswerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new possibleAnswerDTO, or with status 400 (Bad Request) if the possibleAnswer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/possible-answers")
    public ResponseEntity<PossibleAnswerDTO> createPossibleAnswer(@Valid @RequestBody PossibleAnswerDTO possibleAnswerDTO) throws URISyntaxException {
        log.debug("REST request to save PossibleAnswer : {}", possibleAnswerDTO);
        if (possibleAnswerDTO.getId() != null) {
            throw new BadRequestAlertException("A new possibleAnswer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PossibleAnswerDTO result = possibleAnswerService.save(possibleAnswerDTO);
        return ResponseEntity.created(new URI("/api/possible-answers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /possible-answers : Updates an existing possibleAnswer.
     *
     * @param possibleAnswerDTO the possibleAnswerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated possibleAnswerDTO,
     * or with status 400 (Bad Request) if the possibleAnswerDTO is not valid,
     * or with status 500 (Internal Server Error) if the possibleAnswerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/possible-answers")
    public ResponseEntity<PossibleAnswerDTO> updatePossibleAnswer(@Valid @RequestBody PossibleAnswerDTO possibleAnswerDTO) throws URISyntaxException {
        log.debug("REST request to update PossibleAnswer : {}", possibleAnswerDTO);
        if (possibleAnswerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PossibleAnswerDTO result = possibleAnswerService.save(possibleAnswerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, possibleAnswerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /possible-answers : get all the possibleAnswers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of possibleAnswers in body
     */
    @GetMapping("/possible-answers")
    public List<PossibleAnswerDTO> getAllPossibleAnswers() {
        log.debug("REST request to get all PossibleAnswers");
        return possibleAnswerService.findAll();
    }

    /**
     * GET  /possible-answers/:id : get the "id" possibleAnswer.
     *
     * @param id the id of the possibleAnswerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the possibleAnswerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/possible-answers/{id}")
    public ResponseEntity<PossibleAnswerDTO> getPossibleAnswer(@PathVariable Long id) {
        log.debug("REST request to get PossibleAnswer : {}", id);
        Optional<PossibleAnswerDTO> possibleAnswerDTO = possibleAnswerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(possibleAnswerDTO);
    }

    /**
     * DELETE  /possible-answers/:id : delete the "id" possibleAnswer.
     *
     * @param id the id of the possibleAnswerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/possible-answers/{id}")
    public ResponseEntity<Void> deletePossibleAnswer(@PathVariable Long id) {
        log.debug("REST request to delete PossibleAnswer : {}", id);
        possibleAnswerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}

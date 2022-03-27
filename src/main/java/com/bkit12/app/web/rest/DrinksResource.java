package com.bkit12.app.web.rest;

import com.bkit12.app.repository.DrinksRepository;
import com.bkit12.app.service.DrinksService;
import com.bkit12.app.service.dto.DrinksDTO;
import com.bkit12.app.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bkit12.app.domain.Drinks}.
 */
@RestController
@RequestMapping("/api")
public class DrinksResource {

    private final Logger log = LoggerFactory.getLogger(DrinksResource.class);

    private static final String ENTITY_NAME = "cafeAppDrinks";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DrinksService drinksService;

    private final DrinksRepository drinksRepository;

    public DrinksResource(DrinksService drinksService, DrinksRepository drinksRepository) {
        this.drinksService = drinksService;
        this.drinksRepository = drinksRepository;
    }

    /**
     * {@code POST  /drinks} : Create a new drinks.
     *
     * @param drinksDTO the drinksDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new drinksDTO, or with status {@code 400 (Bad Request)} if the drinks has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/drinks")
    public ResponseEntity<DrinksDTO> createDrinks(@Valid @RequestBody DrinksDTO drinksDTO) throws URISyntaxException {
        log.debug("REST request to save Drinks : {}", drinksDTO);
        if (drinksDTO.getId() != null) {
            throw new BadRequestAlertException("A new drinks cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DrinksDTO result = drinksService.save(drinksDTO);
        return ResponseEntity
            .created(new URI("/api/drinks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /drinks/:id} : Updates an existing drinks.
     *
     * @param id the id of the drinksDTO to save.
     * @param drinksDTO the drinksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drinksDTO,
     * or with status {@code 400 (Bad Request)} if the drinksDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the drinksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/drinks/{id}")
    public ResponseEntity<DrinksDTO> updateDrinks(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody DrinksDTO drinksDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Drinks : {}, {}", id, drinksDTO);
        if (drinksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drinksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drinksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        DrinksDTO result = drinksService.save(drinksDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drinksDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /drinks/:id} : Partial updates given fields of an existing drinks, field will ignore if it is null
     *
     * @param id the id of the drinksDTO to save.
     * @param drinksDTO the drinksDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated drinksDTO,
     * or with status {@code 400 (Bad Request)} if the drinksDTO is not valid,
     * or with status {@code 404 (Not Found)} if the drinksDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the drinksDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/drinks/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DrinksDTO> partialUpdateDrinks(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody DrinksDTO drinksDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Drinks partially : {}, {}", id, drinksDTO);
        if (drinksDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, drinksDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!drinksRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DrinksDTO> result = drinksService.partialUpdate(drinksDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, drinksDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /drinks} : get all the drinks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of drinks in body.
     */
    @GetMapping("/drinks")
    public ResponseEntity<List<DrinksDTO>> getAllDrinks(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Drinks");
        Page<DrinksDTO> page = drinksService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /drinks/:id} : get the "id" drinks.
     *
     * @param id the id of the drinksDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the drinksDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/drinks/{id}")
    public ResponseEntity<DrinksDTO> getDrinks(@PathVariable Long id) {
        log.debug("REST request to get Drinks : {}", id);
        Optional<DrinksDTO> drinksDTO = drinksService.findOne(id);
        return ResponseUtil.wrapOrNotFound(drinksDTO);
    }

    /**
     * {@code DELETE  /drinks/:id} : delete the "id" drinks.
     *
     * @param id the id of the drinksDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/drinks/{id}")
    public ResponseEntity<Void> deleteDrinks(@PathVariable Long id) {
        log.debug("REST request to delete Drinks : {}", id);
        drinksService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

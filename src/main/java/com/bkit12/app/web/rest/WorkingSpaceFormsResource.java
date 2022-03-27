package com.bkit12.app.web.rest;

import com.bkit12.app.repository.WorkingSpaceFormsRepository;
import com.bkit12.app.service.WorkingSpaceFormsService;
import com.bkit12.app.service.dto.WorkingSpaceFormsDTO;
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
 * REST controller for managing {@link com.bkit12.app.domain.WorkingSpaceForms}.
 */
@RestController
@RequestMapping("/api")
public class WorkingSpaceFormsResource {

    private final Logger log = LoggerFactory.getLogger(WorkingSpaceFormsResource.class);

    private static final String ENTITY_NAME = "cafeAppWorkingSpaceForms";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WorkingSpaceFormsService workingSpaceFormsService;

    private final WorkingSpaceFormsRepository workingSpaceFormsRepository;

    public WorkingSpaceFormsResource(
        WorkingSpaceFormsService workingSpaceFormsService,
        WorkingSpaceFormsRepository workingSpaceFormsRepository
    ) {
        this.workingSpaceFormsService = workingSpaceFormsService;
        this.workingSpaceFormsRepository = workingSpaceFormsRepository;
    }

    /**
     * {@code POST  /working-space-forms} : Create a new workingSpaceForms.
     *
     * @param workingSpaceFormsDTO the workingSpaceFormsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new workingSpaceFormsDTO, or with status {@code 400 (Bad Request)} if the workingSpaceForms has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/working-space-forms")
    public ResponseEntity<WorkingSpaceFormsDTO> createWorkingSpaceForms(@Valid @RequestBody WorkingSpaceFormsDTO workingSpaceFormsDTO)
        throws URISyntaxException {
        log.debug("REST request to save WorkingSpaceForms : {}", workingSpaceFormsDTO);
        if (workingSpaceFormsDTO.getId() != null) {
            throw new BadRequestAlertException("A new workingSpaceForms cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkingSpaceFormsDTO result = workingSpaceFormsService.save(workingSpaceFormsDTO);
        return ResponseEntity
            .created(new URI("/api/working-space-forms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /working-space-forms/:id} : Updates an existing workingSpaceForms.
     *
     * @param id the id of the workingSpaceFormsDTO to save.
     * @param workingSpaceFormsDTO the workingSpaceFormsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingSpaceFormsDTO,
     * or with status {@code 400 (Bad Request)} if the workingSpaceFormsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the workingSpaceFormsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/working-space-forms/{id}")
    public ResponseEntity<WorkingSpaceFormsDTO> updateWorkingSpaceForms(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody WorkingSpaceFormsDTO workingSpaceFormsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update WorkingSpaceForms : {}, {}", id, workingSpaceFormsDTO);
        if (workingSpaceFormsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingSpaceFormsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingSpaceFormsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        WorkingSpaceFormsDTO result = workingSpaceFormsService.save(workingSpaceFormsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingSpaceFormsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /working-space-forms/:id} : Partial updates given fields of an existing workingSpaceForms, field will ignore if it is null
     *
     * @param id the id of the workingSpaceFormsDTO to save.
     * @param workingSpaceFormsDTO the workingSpaceFormsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated workingSpaceFormsDTO,
     * or with status {@code 400 (Bad Request)} if the workingSpaceFormsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the workingSpaceFormsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the workingSpaceFormsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/working-space-forms/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<WorkingSpaceFormsDTO> partialUpdateWorkingSpaceForms(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody WorkingSpaceFormsDTO workingSpaceFormsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update WorkingSpaceForms partially : {}, {}", id, workingSpaceFormsDTO);
        if (workingSpaceFormsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, workingSpaceFormsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!workingSpaceFormsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<WorkingSpaceFormsDTO> result = workingSpaceFormsService.partialUpdate(workingSpaceFormsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, workingSpaceFormsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /working-space-forms} : get all the workingSpaceForms.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of workingSpaceForms in body.
     */
    @GetMapping("/working-space-forms")
    public ResponseEntity<List<WorkingSpaceFormsDTO>> getAllWorkingSpaceForms(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "true") boolean eagerload
    ) {
        log.debug("REST request to get a page of WorkingSpaceForms");
        Page<WorkingSpaceFormsDTO> page;
        if (eagerload) {
            page = workingSpaceFormsService.findAllWithEagerRelationships(pageable);
        } else {
            page = workingSpaceFormsService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /working-space-forms/:id} : get the "id" workingSpaceForms.
     *
     * @param id the id of the workingSpaceFormsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the workingSpaceFormsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/working-space-forms/{id}")
    public ResponseEntity<WorkingSpaceFormsDTO> getWorkingSpaceForms(@PathVariable Long id) {
        log.debug("REST request to get WorkingSpaceForms : {}", id);
        Optional<WorkingSpaceFormsDTO> workingSpaceFormsDTO = workingSpaceFormsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(workingSpaceFormsDTO);
    }

    /**
     * {@code DELETE  /working-space-forms/:id} : delete the "id" workingSpaceForms.
     *
     * @param id the id of the workingSpaceFormsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/working-space-forms/{id}")
    public ResponseEntity<Void> deleteWorkingSpaceForms(@PathVariable Long id) {
        log.debug("REST request to delete WorkingSpaceForms : {}", id);
        workingSpaceFormsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

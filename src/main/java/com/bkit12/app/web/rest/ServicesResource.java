package com.bkit12.app.web.rest;

import com.bkit12.app.repository.ServicesRepository;
import com.bkit12.app.service.ServicesService;
import com.bkit12.app.service.dto.ServicesDTO;
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
 * REST controller for managing {@link com.bkit12.app.domain.Services}.
 */
@RestController
@RequestMapping("/api")
public class ServicesResource {

    private final Logger log = LoggerFactory.getLogger(ServicesResource.class);

    private static final String ENTITY_NAME = "cafeAppServices";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServicesService servicesService;

    private final ServicesRepository servicesRepository;

    public ServicesResource(ServicesService servicesService, ServicesRepository servicesRepository) {
        this.servicesService = servicesService;
        this.servicesRepository = servicesRepository;
    }

    /**
     * {@code POST  /services} : Create a new services.
     *
     * @param servicesDTO the servicesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new servicesDTO, or with status {@code 400 (Bad Request)} if the services has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/services")
    public ResponseEntity<ServicesDTO> createServices(@Valid @RequestBody ServicesDTO servicesDTO) throws URISyntaxException {
        log.debug("REST request to save Services : {}", servicesDTO);
        if (servicesDTO.getId() != null) {
            throw new BadRequestAlertException("A new services cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServicesDTO result = servicesService.save(servicesDTO);
        return ResponseEntity
            .created(new URI("/api/services/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /services/:id} : Updates an existing services.
     *
     * @param id the id of the servicesDTO to save.
     * @param servicesDTO the servicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicesDTO,
     * or with status {@code 400 (Bad Request)} if the servicesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the servicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/services/{id}")
    public ResponseEntity<ServicesDTO> updateServices(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody ServicesDTO servicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Services : {}, {}", id, servicesDTO);
        if (servicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ServicesDTO result = servicesService.save(servicesDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /services/:id} : Partial updates given fields of an existing services, field will ignore if it is null
     *
     * @param id the id of the servicesDTO to save.
     * @param servicesDTO the servicesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated servicesDTO,
     * or with status {@code 400 (Bad Request)} if the servicesDTO is not valid,
     * or with status {@code 404 (Not Found)} if the servicesDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the servicesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/services/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ServicesDTO> partialUpdateServices(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody ServicesDTO servicesDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Services partially : {}, {}", id, servicesDTO);
        if (servicesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, servicesDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!servicesRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ServicesDTO> result = servicesService.partialUpdate(servicesDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, servicesDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /services} : get all the services.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of services in body.
     */
    @GetMapping("/services")
    public ResponseEntity<List<ServicesDTO>> getAllServices(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Services");
        Page<ServicesDTO> page = servicesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /services/:id} : get the "id" services.
     *
     * @param id the id of the servicesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the servicesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/services/{id}")
    public ResponseEntity<ServicesDTO> getServices(@PathVariable Long id) {
        log.debug("REST request to get Services : {}", id);
        Optional<ServicesDTO> servicesDTO = servicesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(servicesDTO);
    }

    /**
     * {@code DELETE  /services/:id} : delete the "id" services.
     *
     * @param id the id of the servicesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/services/{id}")
    public ResponseEntity<Void> deleteServices(@PathVariable Long id) {
        log.debug("REST request to delete Services : {}", id);
        servicesService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

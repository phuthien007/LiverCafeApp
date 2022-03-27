package com.bkit12.app.web.rest;

import com.bkit12.app.repository.FoodsRepository;
import com.bkit12.app.service.FoodsService;
import com.bkit12.app.service.dto.FoodsDTO;
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
 * REST controller for managing {@link com.bkit12.app.domain.Foods}.
 */
@RestController
@RequestMapping("/api")
public class FoodsResource {

    private final Logger log = LoggerFactory.getLogger(FoodsResource.class);

    private static final String ENTITY_NAME = "cafeAppFoods";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FoodsService foodsService;

    private final FoodsRepository foodsRepository;

    public FoodsResource(FoodsService foodsService, FoodsRepository foodsRepository) {
        this.foodsService = foodsService;
        this.foodsRepository = foodsRepository;
    }

    /**
     * {@code POST  /foods} : Create a new foods.
     *
     * @param foodsDTO the foodsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new foodsDTO, or with status {@code 400 (Bad Request)} if the foods has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/foods")
    public ResponseEntity<FoodsDTO> createFoods(@Valid @RequestBody FoodsDTO foodsDTO) throws URISyntaxException {
        log.debug("REST request to save Foods : {}", foodsDTO);
        if (foodsDTO.getId() != null) {
            throw new BadRequestAlertException("A new foods cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FoodsDTO result = foodsService.save(foodsDTO);
        return ResponseEntity
            .created(new URI("/api/foods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /foods/:id} : Updates an existing foods.
     *
     * @param id the id of the foodsDTO to save.
     * @param foodsDTO the foodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodsDTO,
     * or with status {@code 400 (Bad Request)} if the foodsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the foodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/foods/{id}")
    public ResponseEntity<FoodsDTO> updateFoods(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody FoodsDTO foodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Foods : {}, {}", id, foodsDTO);
        if (foodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FoodsDTO result = foodsService.save(foodsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /foods/:id} : Partial updates given fields of an existing foods, field will ignore if it is null
     *
     * @param id the id of the foodsDTO to save.
     * @param foodsDTO the foodsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated foodsDTO,
     * or with status {@code 400 (Bad Request)} if the foodsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the foodsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the foodsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/foods/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FoodsDTO> partialUpdateFoods(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody FoodsDTO foodsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Foods partially : {}, {}", id, foodsDTO);
        if (foodsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, foodsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!foodsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FoodsDTO> result = foodsService.partialUpdate(foodsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, foodsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /foods} : get all the foods.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of foods in body.
     */
    @GetMapping("/foods")
    public ResponseEntity<List<FoodsDTO>> getAllFoods(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of Foods");
        Page<FoodsDTO> page = foodsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /foods/:id} : get the "id" foods.
     *
     * @param id the id of the foodsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the foodsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/foods/{id}")
    public ResponseEntity<FoodsDTO> getFoods(@PathVariable Long id) {
        log.debug("REST request to get Foods : {}", id);
        Optional<FoodsDTO> foodsDTO = foodsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(foodsDTO);
    }

    /**
     * {@code DELETE  /foods/:id} : delete the "id" foods.
     *
     * @param id the id of the foodsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/foods/{id}")
    public ResponseEntity<Void> deleteFoods(@PathVariable Long id) {
        log.debug("REST request to delete Foods : {}", id);
        foodsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}

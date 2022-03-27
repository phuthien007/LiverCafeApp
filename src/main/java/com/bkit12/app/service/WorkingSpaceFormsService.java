package com.bkit12.app.service;

import com.bkit12.app.domain.WorkingSpaceForms;
import com.bkit12.app.repository.WorkingSpaceFormsRepository;
import com.bkit12.app.service.dto.WorkingSpaceFormsDTO;
import com.bkit12.app.service.mapper.WorkingSpaceFormsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkingSpaceForms}.
 */
@Service
@Transactional
public class WorkingSpaceFormsService {

    private final Logger log = LoggerFactory.getLogger(WorkingSpaceFormsService.class);

    private final WorkingSpaceFormsRepository workingSpaceFormsRepository;

    private final WorkingSpaceFormsMapper workingSpaceFormsMapper;

    public WorkingSpaceFormsService(
        WorkingSpaceFormsRepository workingSpaceFormsRepository,
        WorkingSpaceFormsMapper workingSpaceFormsMapper
    ) {
        this.workingSpaceFormsRepository = workingSpaceFormsRepository;
        this.workingSpaceFormsMapper = workingSpaceFormsMapper;
    }

    /**
     * Save a workingSpaceForms.
     *
     * @param workingSpaceFormsDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingSpaceFormsDTO save(WorkingSpaceFormsDTO workingSpaceFormsDTO) {
        log.debug("Request to save WorkingSpaceForms : {}", workingSpaceFormsDTO);
        WorkingSpaceForms workingSpaceForms = workingSpaceFormsMapper.toEntity(workingSpaceFormsDTO);
        workingSpaceForms = workingSpaceFormsRepository.save(workingSpaceForms);
        return workingSpaceFormsMapper.toDto(workingSpaceForms);
    }

    /**
     * Partially update a workingSpaceForms.
     *
     * @param workingSpaceFormsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkingSpaceFormsDTO> partialUpdate(WorkingSpaceFormsDTO workingSpaceFormsDTO) {
        log.debug("Request to partially update WorkingSpaceForms : {}", workingSpaceFormsDTO);

        return workingSpaceFormsRepository
            .findById(workingSpaceFormsDTO.getId())
            .map(existingWorkingSpaceForms -> {
                workingSpaceFormsMapper.partialUpdate(existingWorkingSpaceForms, workingSpaceFormsDTO);

                return existingWorkingSpaceForms;
            })
            .map(workingSpaceFormsRepository::save)
            .map(workingSpaceFormsMapper::toDto);
    }

    /**
     * Get all the workingSpaceForms.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingSpaceFormsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingSpaceForms");
        return workingSpaceFormsRepository.findAll(pageable).map(workingSpaceFormsMapper::toDto);
    }

    /**
     * Get all the workingSpaceForms with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WorkingSpaceFormsDTO> findAllWithEagerRelationships(Pageable pageable) {
        return workingSpaceFormsRepository.findAllWithEagerRelationships(pageable).map(workingSpaceFormsMapper::toDto);
    }

    /**
     * Get one workingSpaceForms by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkingSpaceFormsDTO> findOne(Long id) {
        log.debug("Request to get WorkingSpaceForms : {}", id);
        return workingSpaceFormsRepository.findOneWithEagerRelationships(id).map(workingSpaceFormsMapper::toDto);
    }

    /**
     * Delete the workingSpaceForms by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkingSpaceForms : {}", id);
        workingSpaceFormsRepository.deleteById(id);
    }
}

package com.bkit12.app.service;

import com.bkit12.app.domain.WorkingSpaces;
import com.bkit12.app.repository.WorkingSpacesRepository;
import com.bkit12.app.service.dto.WorkingSpacesDTO;
import com.bkit12.app.service.mapper.WorkingSpacesMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WorkingSpaces}.
 */
@Service
@Transactional
public class WorkingSpacesService {

    private final Logger log = LoggerFactory.getLogger(WorkingSpacesService.class);

    private final WorkingSpacesRepository workingSpacesRepository;

    private final WorkingSpacesMapper workingSpacesMapper;

    public WorkingSpacesService(WorkingSpacesRepository workingSpacesRepository, WorkingSpacesMapper workingSpacesMapper) {
        this.workingSpacesRepository = workingSpacesRepository;
        this.workingSpacesMapper = workingSpacesMapper;
    }

    /**
     * Save a workingSpaces.
     *
     * @param workingSpacesDTO the entity to save.
     * @return the persisted entity.
     */
    public WorkingSpacesDTO save(WorkingSpacesDTO workingSpacesDTO) {
        log.debug("Request to save WorkingSpaces : {}", workingSpacesDTO);
        WorkingSpaces workingSpaces = workingSpacesMapper.toEntity(workingSpacesDTO);
        workingSpaces = workingSpacesRepository.save(workingSpaces);
        return workingSpacesMapper.toDto(workingSpaces);
    }

    /**
     * Partially update a workingSpaces.
     *
     * @param workingSpacesDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<WorkingSpacesDTO> partialUpdate(WorkingSpacesDTO workingSpacesDTO) {
        log.debug("Request to partially update WorkingSpaces : {}", workingSpacesDTO);

        return workingSpacesRepository
            .findById(workingSpacesDTO.getId())
            .map(existingWorkingSpaces -> {
                workingSpacesMapper.partialUpdate(existingWorkingSpaces, workingSpacesDTO);

                return existingWorkingSpaces;
            })
            .map(workingSpacesRepository::save)
            .map(workingSpacesMapper::toDto);
    }

    /**
     * Get all the workingSpaces.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<WorkingSpacesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all WorkingSpaces");
        return workingSpacesRepository.findAll(pageable).map(workingSpacesMapper::toDto);
    }

    /**
     * Get all the workingSpaces with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<WorkingSpacesDTO> findAllWithEagerRelationships(Pageable pageable) {
        return workingSpacesRepository.findAllWithEagerRelationships(pageable).map(workingSpacesMapper::toDto);
    }

    /**
     * Get one workingSpaces by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<WorkingSpacesDTO> findOne(Long id) {
        log.debug("Request to get WorkingSpaces : {}", id);
        return workingSpacesRepository.findOneWithEagerRelationships(id).map(workingSpacesMapper::toDto);
    }

    /**
     * Delete the workingSpaces by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete WorkingSpaces : {}", id);
        workingSpacesRepository.deleteById(id);
    }
}

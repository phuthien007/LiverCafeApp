package com.bkit12.app.service;

import com.bkit12.app.domain.Drinks;
import com.bkit12.app.repository.DrinksRepository;
import com.bkit12.app.service.dto.DrinksDTO;
import com.bkit12.app.service.mapper.DrinksMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Drinks}.
 */
@Service
@Transactional
public class DrinksService {

    private final Logger log = LoggerFactory.getLogger(DrinksService.class);

    private final DrinksRepository drinksRepository;

    private final DrinksMapper drinksMapper;

    public DrinksService(DrinksRepository drinksRepository, DrinksMapper drinksMapper) {
        this.drinksRepository = drinksRepository;
        this.drinksMapper = drinksMapper;
    }

    /**
     * Save a drinks.
     *
     * @param drinksDTO the entity to save.
     * @return the persisted entity.
     */
    public DrinksDTO save(DrinksDTO drinksDTO) {
        log.debug("Request to save Drinks : {}", drinksDTO);
        Drinks drinks = drinksMapper.toEntity(drinksDTO);
        drinks = drinksRepository.save(drinks);
        return drinksMapper.toDto(drinks);
    }

    /**
     * Partially update a drinks.
     *
     * @param drinksDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<DrinksDTO> partialUpdate(DrinksDTO drinksDTO) {
        log.debug("Request to partially update Drinks : {}", drinksDTO);

        return drinksRepository
            .findById(drinksDTO.getId())
            .map(existingDrinks -> {
                drinksMapper.partialUpdate(existingDrinks, drinksDTO);

                return existingDrinks;
            })
            .map(drinksRepository::save)
            .map(drinksMapper::toDto);
    }

    /**
     * Get all the drinks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<DrinksDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Drinks");
        return drinksRepository.findAll(pageable).map(drinksMapper::toDto);
    }

    /**
     * Get one drinks by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<DrinksDTO> findOne(Long id) {
        log.debug("Request to get Drinks : {}", id);
        return drinksRepository.findById(id).map(drinksMapper::toDto);
    }

    /**
     * Delete the drinks by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Drinks : {}", id);
        drinksRepository.deleteById(id);
    }
}

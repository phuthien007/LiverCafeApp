package com.bkit12.app.service;

import com.bkit12.app.domain.Foods;
import com.bkit12.app.repository.FoodsRepository;
import com.bkit12.app.service.dto.FoodsDTO;
import com.bkit12.app.service.mapper.FoodsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Foods}.
 */
@Service
@Transactional
public class FoodsService {

    private final Logger log = LoggerFactory.getLogger(FoodsService.class);

    private final FoodsRepository foodsRepository;

    private final FoodsMapper foodsMapper;

    public FoodsService(FoodsRepository foodsRepository, FoodsMapper foodsMapper) {
        this.foodsRepository = foodsRepository;
        this.foodsMapper = foodsMapper;
    }

    /**
     * Save a foods.
     *
     * @param foodsDTO the entity to save.
     * @return the persisted entity.
     */
    public FoodsDTO save(FoodsDTO foodsDTO) {
        log.debug("Request to save Foods : {}", foodsDTO);
        Foods foods = foodsMapper.toEntity(foodsDTO);
        foods = foodsRepository.save(foods);
        return foodsMapper.toDto(foods);
    }

    /**
     * Partially update a foods.
     *
     * @param foodsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<FoodsDTO> partialUpdate(FoodsDTO foodsDTO) {
        log.debug("Request to partially update Foods : {}", foodsDTO);

        return foodsRepository
            .findById(foodsDTO.getId())
            .map(existingFoods -> {
                foodsMapper.partialUpdate(existingFoods, foodsDTO);

                return existingFoods;
            })
            .map(foodsRepository::save)
            .map(foodsMapper::toDto);
    }

    /**
     * Get all the foods.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FoodsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Foods");
        return foodsRepository.findAll(pageable).map(foodsMapper::toDto);
    }

    /**
     * Get one foods by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FoodsDTO> findOne(Long id) {
        log.debug("Request to get Foods : {}", id);
        return foodsRepository.findById(id).map(foodsMapper::toDto);
    }

    /**
     * Delete the foods by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Foods : {}", id);
        foodsRepository.deleteById(id);
    }
}

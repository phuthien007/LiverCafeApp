package com.bkit12.app.service;

import com.bkit12.app.domain.Customers;
import com.bkit12.app.repository.CustomersRepository;
import com.bkit12.app.service.dto.CustomersDTO;
import com.bkit12.app.service.mapper.CustomersMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Customers}.
 */
@Service
@Transactional
public class CustomersService {

    private final Logger log = LoggerFactory.getLogger(CustomersService.class);

    private final CustomersRepository customersRepository;

    private final CustomersMapper customersMapper;

    public CustomersService(CustomersRepository customersRepository, CustomersMapper customersMapper) {
        this.customersRepository = customersRepository;
        this.customersMapper = customersMapper;
    }

    /**
     * Save a customers.
     *
     * @param customersDTO the entity to save.
     * @return the persisted entity.
     */
    public CustomersDTO save(CustomersDTO customersDTO) {
        log.debug("Request to save Customers : {}", customersDTO);
        Customers customers = customersMapper.toEntity(customersDTO);
        customers = customersRepository.save(customers);
        return customersMapper.toDto(customers);
    }

    /**
     * Partially update a customers.
     *
     * @param customersDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CustomersDTO> partialUpdate(CustomersDTO customersDTO) {
        log.debug("Request to partially update Customers : {}", customersDTO);

        return customersRepository
            .findById(customersDTO.getId())
            .map(existingCustomers -> {
                customersMapper.partialUpdate(existingCustomers, customersDTO);

                return existingCustomers;
            })
            .map(customersRepository::save)
            .map(customersMapper::toDto);
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CustomersDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return customersRepository.findAll(pageable).map(customersMapper::toDto);
    }

    /**
     * Get one customers by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CustomersDTO> findOne(Long id) {
        log.debug("Request to get Customers : {}", id);
        return customersRepository.findById(id).map(customersMapper::toDto);
    }

    /**
     * Delete the customers by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Customers : {}", id);
        customersRepository.deleteById(id);
    }
}

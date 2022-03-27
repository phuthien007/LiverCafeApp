package com.bkit12.app.service;

import com.bkit12.app.domain.Payments;
import com.bkit12.app.repository.PaymentsRepository;
import com.bkit12.app.service.dto.PaymentsDTO;
import com.bkit12.app.service.mapper.PaymentsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Payments}.
 */
@Service
@Transactional
public class PaymentsService {

    private final Logger log = LoggerFactory.getLogger(PaymentsService.class);

    private final PaymentsRepository paymentsRepository;

    private final PaymentsMapper paymentsMapper;

    public PaymentsService(PaymentsRepository paymentsRepository, PaymentsMapper paymentsMapper) {
        this.paymentsRepository = paymentsRepository;
        this.paymentsMapper = paymentsMapper;
    }

    /**
     * Save a payments.
     *
     * @param paymentsDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentsDTO save(PaymentsDTO paymentsDTO) {
        log.debug("Request to save Payments : {}", paymentsDTO);
        Payments payments = paymentsMapper.toEntity(paymentsDTO);
        payments = paymentsRepository.save(payments);
        return paymentsMapper.toDto(payments);
    }

    /**
     * Partially update a payments.
     *
     * @param paymentsDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentsDTO> partialUpdate(PaymentsDTO paymentsDTO) {
        log.debug("Request to partially update Payments : {}", paymentsDTO);

        return paymentsRepository
            .findById(paymentsDTO.getId())
            .map(existingPayments -> {
                paymentsMapper.partialUpdate(existingPayments, paymentsDTO);

                return existingPayments;
            })
            .map(paymentsRepository::save)
            .map(paymentsMapper::toDto);
    }

    /**
     * Get all the payments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<PaymentsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Payments");
        return paymentsRepository.findAll(pageable).map(paymentsMapper::toDto);
    }

    /**
     *  Get all the payments where WorkingSpaceForm is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsDTO> findAllWhereWorkingSpaceFormIsNull() {
        log.debug("Request to get all payments where WorkingSpaceForm is null");
        return StreamSupport
            .stream(paymentsRepository.findAll().spliterator(), false)
            .filter(payments -> payments.getWorkingSpaceForm() == null)
            .map(paymentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the payments where Order is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentsDTO> findAllWhereOrderIsNull() {
        log.debug("Request to get all payments where Order is null");
        return StreamSupport
            .stream(paymentsRepository.findAll().spliterator(), false)
            .filter(payments -> payments.getOrder() == null)
            .map(paymentsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one payments by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentsDTO> findOne(Long id) {
        log.debug("Request to get Payments : {}", id);
        return paymentsRepository.findById(id).map(paymentsMapper::toDto);
    }

    /**
     * Delete the payments by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Payments : {}", id);
        paymentsRepository.deleteById(id);
    }
}

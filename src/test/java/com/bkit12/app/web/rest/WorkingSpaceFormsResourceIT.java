package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.WorkingSpaceForms;
import com.bkit12.app.domain.enumeration.WorkingSpaceStatus;
import com.bkit12.app.repository.WorkingSpaceFormsRepository;
import com.bkit12.app.service.WorkingSpaceFormsService;
import com.bkit12.app.service.dto.WorkingSpaceFormsDTO;
import com.bkit12.app.service.mapper.WorkingSpaceFormsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link WorkingSpaceFormsResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WorkingSpaceFormsResourceIT {

    private static final Instant DEFAULT_TIME_START = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_START = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_TIME_END = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME_END = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_PRICE_TOTAL = 1L;
    private static final Long UPDATED_PRICE_TOTAL = 2L;

    private static final String DEFAULT_NAME_EVENT = "AAAAAAAAAA";
    private static final String UPDATED_NAME_EVENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY_PEOPLE = 1L;
    private static final Long UPDATED_QUANTITY_PEOPLE = 2L;

    private static final WorkingSpaceStatus DEFAULT_STATUS = WorkingSpaceStatus.INJECTED;
    private static final WorkingSpaceStatus UPDATED_STATUS = WorkingSpaceStatus.PENDING;

    private static final Double DEFAULT_PERCENT_DEPOSIT = 1D;
    private static final Double UPDATED_PERCENT_DEPOSIT = 2D;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/working-space-forms";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkingSpaceFormsRepository workingSpaceFormsRepository;

    @Mock
    private WorkingSpaceFormsRepository workingSpaceFormsRepositoryMock;

    @Autowired
    private WorkingSpaceFormsMapper workingSpaceFormsMapper;

    @Mock
    private WorkingSpaceFormsService workingSpaceFormsServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingSpaceFormsMockMvc;

    private WorkingSpaceForms workingSpaceForms;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingSpaceForms createEntity(EntityManager em) {
        WorkingSpaceForms workingSpaceForms = new WorkingSpaceForms()
            .timeStart(DEFAULT_TIME_START)
            .timeEnd(DEFAULT_TIME_END)
            .priceTotal(DEFAULT_PRICE_TOTAL)
            .nameEvent(DEFAULT_NAME_EVENT)
            .note(DEFAULT_NOTE)
            .quantityPeople(DEFAULT_QUANTITY_PEOPLE)
            .status(DEFAULT_STATUS)
            .percentDeposit(DEFAULT_PERCENT_DEPOSIT)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return workingSpaceForms;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingSpaceForms createUpdatedEntity(EntityManager em) {
        WorkingSpaceForms workingSpaceForms = new WorkingSpaceForms()
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .nameEvent(UPDATED_NAME_EVENT)
            .note(UPDATED_NOTE)
            .quantityPeople(UPDATED_QUANTITY_PEOPLE)
            .status(UPDATED_STATUS)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return workingSpaceForms;
    }

    @BeforeEach
    public void initTest() {
        workingSpaceForms = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeCreate = workingSpaceFormsRepository.findAll().size();
        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);
        restWorkingSpaceFormsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingSpaceForms testWorkingSpaceForms = workingSpaceFormsList.get(workingSpaceFormsList.size() - 1);
        assertThat(testWorkingSpaceForms.getTimeStart()).isEqualTo(DEFAULT_TIME_START);
        assertThat(testWorkingSpaceForms.getTimeEnd()).isEqualTo(DEFAULT_TIME_END);
        assertThat(testWorkingSpaceForms.getPriceTotal()).isEqualTo(DEFAULT_PRICE_TOTAL);
        assertThat(testWorkingSpaceForms.getNameEvent()).isEqualTo(DEFAULT_NAME_EVENT);
        assertThat(testWorkingSpaceForms.getNote()).isEqualTo(DEFAULT_NOTE);
        assertThat(testWorkingSpaceForms.getQuantityPeople()).isEqualTo(DEFAULT_QUANTITY_PEOPLE);
        assertThat(testWorkingSpaceForms.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkingSpaceForms.getPercentDeposit()).isEqualTo(DEFAULT_PERCENT_DEPOSIT);
        assertThat(testWorkingSpaceForms.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWorkingSpaceForms.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkingSpaceForms.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testWorkingSpaceForms.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createWorkingSpaceFormsWithExistingId() throws Exception {
        // Create the WorkingSpaceForms with an existing ID
        workingSpaceForms.setId(1L);
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        int databaseSizeBeforeCreate = workingSpaceFormsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingSpaceFormsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkingSpaceForms() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        // Get all the workingSpaceFormsList
        restWorkingSpaceFormsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingSpaceForms.getId().intValue())))
            .andExpect(jsonPath("$.[*].timeStart").value(hasItem(DEFAULT_TIME_START.toString())))
            .andExpect(jsonPath("$.[*].timeEnd").value(hasItem(DEFAULT_TIME_END.toString())))
            .andExpect(jsonPath("$.[*].priceTotal").value(hasItem(DEFAULT_PRICE_TOTAL.intValue())))
            .andExpect(jsonPath("$.[*].nameEvent").value(hasItem(DEFAULT_NAME_EVENT)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].quantityPeople").value(hasItem(DEFAULT_QUANTITY_PEOPLE.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].percentDeposit").value(hasItem(DEFAULT_PERCENT_DEPOSIT.doubleValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkingSpaceFormsWithEagerRelationshipsIsEnabled() throws Exception {
        when(workingSpaceFormsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkingSpaceFormsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workingSpaceFormsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkingSpaceFormsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(workingSpaceFormsServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkingSpaceFormsMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workingSpaceFormsServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getWorkingSpaceForms() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        // Get the workingSpaceForms
        restWorkingSpaceFormsMockMvc
            .perform(get(ENTITY_API_URL_ID, workingSpaceForms.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingSpaceForms.getId().intValue()))
            .andExpect(jsonPath("$.timeStart").value(DEFAULT_TIME_START.toString()))
            .andExpect(jsonPath("$.timeEnd").value(DEFAULT_TIME_END.toString()))
            .andExpect(jsonPath("$.priceTotal").value(DEFAULT_PRICE_TOTAL.intValue()))
            .andExpect(jsonPath("$.nameEvent").value(DEFAULT_NAME_EVENT))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.quantityPeople").value(DEFAULT_QUANTITY_PEOPLE.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.percentDeposit").value(DEFAULT_PERCENT_DEPOSIT.doubleValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorkingSpaceForms() throws Exception {
        // Get the workingSpaceForms
        restWorkingSpaceFormsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkingSpaceForms() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();

        // Update the workingSpaceForms
        WorkingSpaceForms updatedWorkingSpaceForms = workingSpaceFormsRepository.findById(workingSpaceForms.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingSpaceForms are not directly saved in db
        em.detach(updatedWorkingSpaceForms);
        updatedWorkingSpaceForms
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .nameEvent(UPDATED_NAME_EVENT)
            .note(UPDATED_NOTE)
            .quantityPeople(UPDATED_QUANTITY_PEOPLE)
            .status(UPDATED_STATUS)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(updatedWorkingSpaceForms);

        restWorkingSpaceFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingSpaceFormsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaceForms testWorkingSpaceForms = workingSpaceFormsList.get(workingSpaceFormsList.size() - 1);
        assertThat(testWorkingSpaceForms.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testWorkingSpaceForms.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
        assertThat(testWorkingSpaceForms.getPriceTotal()).isEqualTo(UPDATED_PRICE_TOTAL);
        assertThat(testWorkingSpaceForms.getNameEvent()).isEqualTo(UPDATED_NAME_EVENT);
        assertThat(testWorkingSpaceForms.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkingSpaceForms.getQuantityPeople()).isEqualTo(UPDATED_QUANTITY_PEOPLE);
        assertThat(testWorkingSpaceForms.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkingSpaceForms.getPercentDeposit()).isEqualTo(UPDATED_PERCENT_DEPOSIT);
        assertThat(testWorkingSpaceForms.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaceForms.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingSpaceForms.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testWorkingSpaceForms.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingSpaceFormsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkingSpaceFormsWithPatch() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();

        // Update the workingSpaceForms using partial update
        WorkingSpaceForms partialUpdatedWorkingSpaceForms = new WorkingSpaceForms();
        partialUpdatedWorkingSpaceForms.setId(workingSpaceForms.getId());

        partialUpdatedWorkingSpaceForms
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END)
            .note(UPDATED_NOTE)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .createdDate(UPDATED_CREATED_DATE);

        restWorkingSpaceFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingSpaceForms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingSpaceForms))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaceForms testWorkingSpaceForms = workingSpaceFormsList.get(workingSpaceFormsList.size() - 1);
        assertThat(testWorkingSpaceForms.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testWorkingSpaceForms.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
        assertThat(testWorkingSpaceForms.getPriceTotal()).isEqualTo(DEFAULT_PRICE_TOTAL);
        assertThat(testWorkingSpaceForms.getNameEvent()).isEqualTo(DEFAULT_NAME_EVENT);
        assertThat(testWorkingSpaceForms.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkingSpaceForms.getQuantityPeople()).isEqualTo(DEFAULT_QUANTITY_PEOPLE);
        assertThat(testWorkingSpaceForms.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testWorkingSpaceForms.getPercentDeposit()).isEqualTo(UPDATED_PERCENT_DEPOSIT);
        assertThat(testWorkingSpaceForms.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaceForms.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkingSpaceForms.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testWorkingSpaceForms.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWorkingSpaceFormsWithPatch() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();

        // Update the workingSpaceForms using partial update
        WorkingSpaceForms partialUpdatedWorkingSpaceForms = new WorkingSpaceForms();
        partialUpdatedWorkingSpaceForms.setId(workingSpaceForms.getId());

        partialUpdatedWorkingSpaceForms
            .timeStart(UPDATED_TIME_START)
            .timeEnd(UPDATED_TIME_END)
            .priceTotal(UPDATED_PRICE_TOTAL)
            .nameEvent(UPDATED_NAME_EVENT)
            .note(UPDATED_NOTE)
            .quantityPeople(UPDATED_QUANTITY_PEOPLE)
            .status(UPDATED_STATUS)
            .percentDeposit(UPDATED_PERCENT_DEPOSIT)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restWorkingSpaceFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingSpaceForms.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingSpaceForms))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaceForms testWorkingSpaceForms = workingSpaceFormsList.get(workingSpaceFormsList.size() - 1);
        assertThat(testWorkingSpaceForms.getTimeStart()).isEqualTo(UPDATED_TIME_START);
        assertThat(testWorkingSpaceForms.getTimeEnd()).isEqualTo(UPDATED_TIME_END);
        assertThat(testWorkingSpaceForms.getPriceTotal()).isEqualTo(UPDATED_PRICE_TOTAL);
        assertThat(testWorkingSpaceForms.getNameEvent()).isEqualTo(UPDATED_NAME_EVENT);
        assertThat(testWorkingSpaceForms.getNote()).isEqualTo(UPDATED_NOTE);
        assertThat(testWorkingSpaceForms.getQuantityPeople()).isEqualTo(UPDATED_QUANTITY_PEOPLE);
        assertThat(testWorkingSpaceForms.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testWorkingSpaceForms.getPercentDeposit()).isEqualTo(UPDATED_PERCENT_DEPOSIT);
        assertThat(testWorkingSpaceForms.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaceForms.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingSpaceForms.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testWorkingSpaceForms.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workingSpaceFormsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkingSpaceForms() throws Exception {
        int databaseSizeBeforeUpdate = workingSpaceFormsRepository.findAll().size();
        workingSpaceForms.setId(count.incrementAndGet());

        // Create the WorkingSpaceForms
        WorkingSpaceFormsDTO workingSpaceFormsDTO = workingSpaceFormsMapper.toDto(workingSpaceForms);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpaceFormsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpaceFormsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingSpaceForms in the database
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkingSpaceForms() throws Exception {
        // Initialize the database
        workingSpaceFormsRepository.saveAndFlush(workingSpaceForms);

        int databaseSizeBeforeDelete = workingSpaceFormsRepository.findAll().size();

        // Delete the workingSpaceForms
        restWorkingSpaceFormsMockMvc
            .perform(delete(ENTITY_API_URL_ID, workingSpaceForms.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingSpaceForms> workingSpaceFormsList = workingSpaceFormsRepository.findAll();
        assertThat(workingSpaceFormsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

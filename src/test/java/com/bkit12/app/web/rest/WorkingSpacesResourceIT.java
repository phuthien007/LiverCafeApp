package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.WorkingSpaces;
import com.bkit12.app.repository.WorkingSpacesRepository;
import com.bkit12.app.service.WorkingSpacesService;
import com.bkit12.app.service.dto.WorkingSpacesDTO;
import com.bkit12.app.service.mapper.WorkingSpacesMapper;
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
 * Integration tests for the {@link WorkingSpacesResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class WorkingSpacesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY_CAN_HOLD = 1L;
    private static final Long UPDATED_QUANTITY_CAN_HOLD = 2L;

    private static final Long DEFAULT_PRICE_PER_HOUR = 1L;
    private static final Long UPDATED_PRICE_PER_HOUR = 2L;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/working-spaces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private WorkingSpacesRepository workingSpacesRepository;

    @Mock
    private WorkingSpacesRepository workingSpacesRepositoryMock;

    @Autowired
    private WorkingSpacesMapper workingSpacesMapper;

    @Mock
    private WorkingSpacesService workingSpacesServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkingSpacesMockMvc;

    private WorkingSpaces workingSpaces;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingSpaces createEntity(EntityManager em) {
        WorkingSpaces workingSpaces = new WorkingSpaces()
            .name(DEFAULT_NAME)
            .location(DEFAULT_LOCATION)
            .quantityCanHold(DEFAULT_QUANTITY_CAN_HOLD)
            .pricePerHour(DEFAULT_PRICE_PER_HOUR)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return workingSpaces;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WorkingSpaces createUpdatedEntity(EntityManager em) {
        WorkingSpaces workingSpaces = new WorkingSpaces()
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .quantityCanHold(UPDATED_QUANTITY_CAN_HOLD)
            .pricePerHour(UPDATED_PRICE_PER_HOUR)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return workingSpaces;
    }

    @BeforeEach
    public void initTest() {
        workingSpaces = createEntity(em);
    }

    @Test
    @Transactional
    void createWorkingSpaces() throws Exception {
        int databaseSizeBeforeCreate = workingSpacesRepository.findAll().size();
        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);
        restWorkingSpacesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeCreate + 1);
        WorkingSpaces testWorkingSpaces = workingSpacesList.get(workingSpacesList.size() - 1);
        assertThat(testWorkingSpaces.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkingSpaces.getLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testWorkingSpaces.getQuantityCanHold()).isEqualTo(DEFAULT_QUANTITY_CAN_HOLD);
        assertThat(testWorkingSpaces.getPricePerHour()).isEqualTo(DEFAULT_PRICE_PER_HOUR);
        assertThat(testWorkingSpaces.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testWorkingSpaces.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkingSpaces.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testWorkingSpaces.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createWorkingSpacesWithExistingId() throws Exception {
        // Create the WorkingSpaces with an existing ID
        workingSpaces.setId(1L);
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        int databaseSizeBeforeCreate = workingSpacesRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkingSpacesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingSpacesRepository.findAll().size();
        // set the field null
        workingSpaces.setName(null);

        // Create the WorkingSpaces, which fails.
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        restWorkingSpacesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLocationIsRequired() throws Exception {
        int databaseSizeBeforeTest = workingSpacesRepository.findAll().size();
        // set the field null
        workingSpaces.setLocation(null);

        // Create the WorkingSpaces, which fails.
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        restWorkingSpacesMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllWorkingSpaces() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        // Get all the workingSpacesList
        restWorkingSpacesMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workingSpaces.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)))
            .andExpect(jsonPath("$.[*].quantityCanHold").value(hasItem(DEFAULT_QUANTITY_CAN_HOLD.intValue())))
            .andExpect(jsonPath("$.[*].pricePerHour").value(hasItem(DEFAULT_PRICE_PER_HOUR.intValue())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkingSpacesWithEagerRelationshipsIsEnabled() throws Exception {
        when(workingSpacesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkingSpacesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workingSpacesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllWorkingSpacesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(workingSpacesServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restWorkingSpacesMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(workingSpacesServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getWorkingSpaces() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        // Get the workingSpaces
        restWorkingSpacesMockMvc
            .perform(get(ENTITY_API_URL_ID, workingSpaces.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workingSpaces.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION))
            .andExpect(jsonPath("$.quantityCanHold").value(DEFAULT_QUANTITY_CAN_HOLD.intValue()))
            .andExpect(jsonPath("$.pricePerHour").value(DEFAULT_PRICE_PER_HOUR.intValue()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingWorkingSpaces() throws Exception {
        // Get the workingSpaces
        restWorkingSpacesMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewWorkingSpaces() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();

        // Update the workingSpaces
        WorkingSpaces updatedWorkingSpaces = workingSpacesRepository.findById(workingSpaces.getId()).get();
        // Disconnect from session so that the updates on updatedWorkingSpaces are not directly saved in db
        em.detach(updatedWorkingSpaces);
        updatedWorkingSpaces
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .quantityCanHold(UPDATED_QUANTITY_CAN_HOLD)
            .pricePerHour(UPDATED_PRICE_PER_HOUR)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(updatedWorkingSpaces);

        restWorkingSpacesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingSpacesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaces testWorkingSpaces = workingSpacesList.get(workingSpacesList.size() - 1);
        assertThat(testWorkingSpaces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkingSpaces.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testWorkingSpaces.getQuantityCanHold()).isEqualTo(UPDATED_QUANTITY_CAN_HOLD);
        assertThat(testWorkingSpaces.getPricePerHour()).isEqualTo(UPDATED_PRICE_PER_HOUR);
        assertThat(testWorkingSpaces.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaces.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingSpaces.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testWorkingSpaces.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workingSpacesDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkingSpacesWithPatch() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();

        // Update the workingSpaces using partial update
        WorkingSpaces partialUpdatedWorkingSpaces = new WorkingSpaces();
        partialUpdatedWorkingSpaces.setId(workingSpaces.getId());

        partialUpdatedWorkingSpaces.location(UPDATED_LOCATION).quantityCanHold(UPDATED_QUANTITY_CAN_HOLD).createdDate(UPDATED_CREATED_DATE);

        restWorkingSpacesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingSpaces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingSpaces))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaces testWorkingSpaces = workingSpacesList.get(workingSpacesList.size() - 1);
        assertThat(testWorkingSpaces.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testWorkingSpaces.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testWorkingSpaces.getQuantityCanHold()).isEqualTo(UPDATED_QUANTITY_CAN_HOLD);
        assertThat(testWorkingSpaces.getPricePerHour()).isEqualTo(DEFAULT_PRICE_PER_HOUR);
        assertThat(testWorkingSpaces.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaces.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testWorkingSpaces.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testWorkingSpaces.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateWorkingSpacesWithPatch() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();

        // Update the workingSpaces using partial update
        WorkingSpaces partialUpdatedWorkingSpaces = new WorkingSpaces();
        partialUpdatedWorkingSpaces.setId(workingSpaces.getId());

        partialUpdatedWorkingSpaces
            .name(UPDATED_NAME)
            .location(UPDATED_LOCATION)
            .quantityCanHold(UPDATED_QUANTITY_CAN_HOLD)
            .pricePerHour(UPDATED_PRICE_PER_HOUR)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restWorkingSpacesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkingSpaces.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWorkingSpaces))
            )
            .andExpect(status().isOk());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
        WorkingSpaces testWorkingSpaces = workingSpacesList.get(workingSpacesList.size() - 1);
        assertThat(testWorkingSpaces.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testWorkingSpaces.getLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testWorkingSpaces.getQuantityCanHold()).isEqualTo(UPDATED_QUANTITY_CAN_HOLD);
        assertThat(testWorkingSpaces.getPricePerHour()).isEqualTo(UPDATED_PRICE_PER_HOUR);
        assertThat(testWorkingSpaces.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testWorkingSpaces.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testWorkingSpaces.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testWorkingSpaces.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workingSpacesDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkingSpaces() throws Exception {
        int databaseSizeBeforeUpdate = workingSpacesRepository.findAll().size();
        workingSpaces.setId(count.incrementAndGet());

        // Create the WorkingSpaces
        WorkingSpacesDTO workingSpacesDTO = workingSpacesMapper.toDto(workingSpaces);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkingSpacesMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(workingSpacesDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the WorkingSpaces in the database
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkingSpaces() throws Exception {
        // Initialize the database
        workingSpacesRepository.saveAndFlush(workingSpaces);

        int databaseSizeBeforeDelete = workingSpacesRepository.findAll().size();

        // Delete the workingSpaces
        restWorkingSpacesMockMvc
            .perform(delete(ENTITY_API_URL_ID, workingSpaces.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WorkingSpaces> workingSpacesList = workingSpacesRepository.findAll();
        assertThat(workingSpacesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

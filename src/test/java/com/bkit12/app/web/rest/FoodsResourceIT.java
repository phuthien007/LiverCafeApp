package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.Foods;
import com.bkit12.app.domain.enumeration.CommonStatus;
import com.bkit12.app.repository.FoodsRepository;
import com.bkit12.app.service.dto.FoodsDTO;
import com.bkit12.app.service.mapper.FoodsMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FoodsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FoodsResourceIT {

    private static final String DEFAULT_ID_FOOD = "AAAAAAAAAA";
    private static final String UPDATED_ID_FOOD = "BBBBBBBBBB";

    private static final String DEFAULT_NAM = "AAAAAAAAAA";
    private static final String UPDATED_NAM = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final CommonStatus DEFAULT_STATUS = CommonStatus.SOLD_OUT;
    private static final CommonStatus UPDATED_STATUS = CommonStatus.NEW;

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/foods";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FoodsRepository foodsRepository;

    @Autowired
    private FoodsMapper foodsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFoodsMockMvc;

    private Foods foods;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foods createEntity(EntityManager em) {
        Foods foods = new Foods()
            .idFood(DEFAULT_ID_FOOD)
            .nam(DEFAULT_NAM)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return foods;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Foods createUpdatedEntity(EntityManager em) {
        Foods foods = new Foods()
            .idFood(UPDATED_ID_FOOD)
            .nam(UPDATED_NAM)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return foods;
    }

    @BeforeEach
    public void initTest() {
        foods = createEntity(em);
    }

    @Test
    @Transactional
    void createFoods() throws Exception {
        int databaseSizeBeforeCreate = foodsRepository.findAll().size();
        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);
        restFoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isCreated());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeCreate + 1);
        Foods testFoods = foodsList.get(foodsList.size() - 1);
        assertThat(testFoods.getIdFood()).isEqualTo(DEFAULT_ID_FOOD);
        assertThat(testFoods.getNam()).isEqualTo(DEFAULT_NAM);
        assertThat(testFoods.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoods.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFoods.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFoods.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFoods.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testFoods.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testFoods.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createFoodsWithExistingId() throws Exception {
        // Create the Foods with an existing ID
        foods.setId(1L);
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        int databaseSizeBeforeCreate = foodsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdFoodIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodsRepository.findAll().size();
        // set the field null
        foods.setIdFood(null);

        // Create the Foods, which fails.
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        restFoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isBadRequest());

        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNamIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodsRepository.findAll().size();
        // set the field null
        foods.setNam(null);

        // Create the Foods, which fails.
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        restFoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isBadRequest());

        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = foodsRepository.findAll().size();
        // set the field null
        foods.setPrice(null);

        // Create the Foods, which fails.
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        restFoodsMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isBadRequest());

        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFoods() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        // Get all the foodsList
        restFoodsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(foods.getId().intValue())))
            .andExpect(jsonPath("$.[*].idFood").value(hasItem(DEFAULT_ID_FOOD)))
            .andExpect(jsonPath("$.[*].nam").value(hasItem(DEFAULT_NAM)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }

    @Test
    @Transactional
    void getFoods() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        // Get the foods
        restFoodsMockMvc
            .perform(get(ENTITY_API_URL_ID, foods.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(foods.getId().intValue()))
            .andExpect(jsonPath("$.idFood").value(DEFAULT_ID_FOOD))
            .andExpect(jsonPath("$.nam").value(DEFAULT_NAM))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFoods() throws Exception {
        // Get the foods
        restFoodsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewFoods() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();

        // Update the foods
        Foods updatedFoods = foodsRepository.findById(foods.getId()).get();
        // Disconnect from session so that the updates on updatedFoods are not directly saved in db
        em.detach(updatedFoods);
        updatedFoods
            .idFood(UPDATED_ID_FOOD)
            .nam(UPDATED_NAM)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        FoodsDTO foodsDTO = foodsMapper.toDto(updatedFoods);

        restFoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
        Foods testFoods = foodsList.get(foodsList.size() - 1);
        assertThat(testFoods.getIdFood()).isEqualTo(UPDATED_ID_FOOD);
        assertThat(testFoods.getNam()).isEqualTo(UPDATED_NAM);
        assertThat(testFoods.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoods.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoods.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFoods.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFoods.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoods.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testFoods.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, foodsDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(foodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFoodsWithPatch() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();

        // Update the foods using partial update
        Foods partialUpdatedFoods = new Foods();
        partialUpdatedFoods.setId(foods.getId());

        partialUpdatedFoods.description(UPDATED_DESCRIPTION).createdBy(UPDATED_CREATED_BY);

        restFoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoods))
            )
            .andExpect(status().isOk());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
        Foods testFoods = foodsList.get(foodsList.size() - 1);
        assertThat(testFoods.getIdFood()).isEqualTo(DEFAULT_ID_FOOD);
        assertThat(testFoods.getNam()).isEqualTo(DEFAULT_NAM);
        assertThat(testFoods.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testFoods.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoods.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testFoods.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testFoods.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoods.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testFoods.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateFoodsWithPatch() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();

        // Update the foods using partial update
        Foods partialUpdatedFoods = new Foods();
        partialUpdatedFoods.setId(foods.getId());

        partialUpdatedFoods
            .idFood(UPDATED_ID_FOOD)
            .nam(UPDATED_NAM)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restFoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFoods.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFoods))
            )
            .andExpect(status().isOk());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
        Foods testFoods = foodsList.get(foodsList.size() - 1);
        assertThat(testFoods.getIdFood()).isEqualTo(UPDATED_ID_FOOD);
        assertThat(testFoods.getNam()).isEqualTo(UPDATED_NAM);
        assertThat(testFoods.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testFoods.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFoods.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testFoods.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testFoods.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testFoods.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testFoods.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, foodsDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(foodsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFoods() throws Exception {
        int databaseSizeBeforeUpdate = foodsRepository.findAll().size();
        foods.setId(count.incrementAndGet());

        // Create the Foods
        FoodsDTO foodsDTO = foodsMapper.toDto(foods);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFoodsMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(foodsDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Foods in the database
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFoods() throws Exception {
        // Initialize the database
        foodsRepository.saveAndFlush(foods);

        int databaseSizeBeforeDelete = foodsRepository.findAll().size();

        // Delete the foods
        restFoodsMockMvc
            .perform(delete(ENTITY_API_URL_ID, foods.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Foods> foodsList = foodsRepository.findAll();
        assertThat(foodsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.Drinks;
import com.bkit12.app.domain.enumeration.CommonStatus;
import com.bkit12.app.repository.DrinksRepository;
import com.bkit12.app.service.dto.DrinksDTO;
import com.bkit12.app.service.mapper.DrinksMapper;
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
 * Integration tests for the {@link DrinksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class DrinksResourceIT {

    private static final String DEFAULT_ID_DRINK = "AAAAAAAAAA";
    private static final String UPDATED_ID_DRINK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/drinks";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private DrinksRepository drinksRepository;

    @Autowired
    private DrinksMapper drinksMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDrinksMockMvc;

    private Drinks drinks;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drinks createEntity(EntityManager em) {
        Drinks drinks = new Drinks()
            .idDrink(DEFAULT_ID_DRINK)
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .description(DEFAULT_DESCRIPTION)
            .status(DEFAULT_STATUS)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return drinks;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Drinks createUpdatedEntity(EntityManager em) {
        Drinks drinks = new Drinks()
            .idDrink(UPDATED_ID_DRINK)
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return drinks;
    }

    @BeforeEach
    public void initTest() {
        drinks = createEntity(em);
    }

    @Test
    @Transactional
    void createDrinks() throws Exception {
        int databaseSizeBeforeCreate = drinksRepository.findAll().size();
        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);
        restDrinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isCreated());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeCreate + 1);
        Drinks testDrinks = drinksList.get(drinksList.size() - 1);
        assertThat(testDrinks.getIdDrink()).isEqualTo(DEFAULT_ID_DRINK);
        assertThat(testDrinks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDrinks.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testDrinks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDrinks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDrinks.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testDrinks.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDrinks.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testDrinks.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createDrinksWithExistingId() throws Exception {
        // Create the Drinks with an existing ID
        drinks.setId(1L);
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        int databaseSizeBeforeCreate = drinksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restDrinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdDrinkIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinksRepository.findAll().size();
        // set the field null
        drinks.setIdDrink(null);

        // Create the Drinks, which fails.
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        restDrinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isBadRequest());

        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinksRepository.findAll().size();
        // set the field null
        drinks.setName(null);

        // Create the Drinks, which fails.
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        restDrinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isBadRequest());

        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = drinksRepository.findAll().size();
        // set the field null
        drinks.setPrice(null);

        // Create the Drinks, which fails.
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        restDrinksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isBadRequest());

        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllDrinks() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        // Get all the drinksList
        restDrinksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(drinks.getId().intValue())))
            .andExpect(jsonPath("$.[*].idDrink").value(hasItem(DEFAULT_ID_DRINK)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
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
    void getDrinks() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        // Get the drinks
        restDrinksMockMvc
            .perform(get(ENTITY_API_URL_ID, drinks.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(drinks.getId().intValue()))
            .andExpect(jsonPath("$.idDrink").value(DEFAULT_ID_DRINK))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
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
    void getNonExistingDrinks() throws Exception {
        // Get the drinks
        restDrinksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewDrinks() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();

        // Update the drinks
        Drinks updatedDrinks = drinksRepository.findById(drinks.getId()).get();
        // Disconnect from session so that the updates on updatedDrinks are not directly saved in db
        em.detach(updatedDrinks);
        updatedDrinks
            .idDrink(UPDATED_ID_DRINK)
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        DrinksDTO drinksDTO = drinksMapper.toDto(updatedDrinks);

        restDrinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drinksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isOk());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
        Drinks testDrinks = drinksList.get(drinksList.size() - 1);
        assertThat(testDrinks.getIdDrink()).isEqualTo(UPDATED_ID_DRINK);
        assertThat(testDrinks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrinks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrinks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDrinks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDrinks.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDrinks.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDrinks.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDrinks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, drinksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(drinksDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateDrinksWithPatch() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();

        // Update the drinks using partial update
        Drinks partialUpdatedDrinks = new Drinks();
        partialUpdatedDrinks.setId(drinks.getId());

        partialUpdatedDrinks
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .createdDate(UPDATED_CREATED_DATE)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restDrinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrinks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrinks))
            )
            .andExpect(status().isOk());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
        Drinks testDrinks = drinksList.get(drinksList.size() - 1);
        assertThat(testDrinks.getIdDrink()).isEqualTo(DEFAULT_ID_DRINK);
        assertThat(testDrinks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrinks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrinks.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testDrinks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testDrinks.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDrinks.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testDrinks.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDrinks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateDrinksWithPatch() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();

        // Update the drinks using partial update
        Drinks partialUpdatedDrinks = new Drinks();
        partialUpdatedDrinks.setId(drinks.getId());

        partialUpdatedDrinks
            .idDrink(UPDATED_ID_DRINK)
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .description(UPDATED_DESCRIPTION)
            .status(UPDATED_STATUS)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restDrinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedDrinks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedDrinks))
            )
            .andExpect(status().isOk());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
        Drinks testDrinks = drinksList.get(drinksList.size() - 1);
        assertThat(testDrinks.getIdDrink()).isEqualTo(UPDATED_ID_DRINK);
        assertThat(testDrinks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDrinks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testDrinks.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testDrinks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testDrinks.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testDrinks.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testDrinks.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testDrinks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, drinksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamDrinks() throws Exception {
        int databaseSizeBeforeUpdate = drinksRepository.findAll().size();
        drinks.setId(count.incrementAndGet());

        // Create the Drinks
        DrinksDTO drinksDTO = drinksMapper.toDto(drinks);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restDrinksMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(drinksDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Drinks in the database
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteDrinks() throws Exception {
        // Initialize the database
        drinksRepository.saveAndFlush(drinks);

        int databaseSizeBeforeDelete = drinksRepository.findAll().size();

        // Delete the drinks
        restDrinksMockMvc
            .perform(delete(ENTITY_API_URL_ID, drinks.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Drinks> drinksList = drinksRepository.findAll();
        assertThat(drinksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

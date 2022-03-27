package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.Vouchers;
import com.bkit12.app.repository.VouchersRepository;
import com.bkit12.app.service.VouchersService;
import com.bkit12.app.service.dto.VouchersDTO;
import com.bkit12.app.service.mapper.VouchersMapper;
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
 * Integration tests for the {@link VouchersResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class VouchersResourceIT {

    private static final String DEFAULT_ID_VOUCHER = "AAAAAAAAAA";
    private static final String UPDATED_ID_VOUCHER = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_PERCENT_PROMOTION = 1D;
    private static final Double UPDATED_PERCENT_PROMOTION = 2D;

    private static final Double DEFAULT_MAX_TOTAL_MONEY_PROMOTION = 1D;
    private static final Double UPDATED_MAX_TOTAL_MONEY_PROMOTION = 2D;

    private static final Boolean DEFAULT_ACTIVE = false;
    private static final Boolean UPDATED_ACTIVE = true;

    private static final Instant DEFAULT_START_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_START_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_END_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_END_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/vouchers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private VouchersRepository vouchersRepository;

    @Mock
    private VouchersRepository vouchersRepositoryMock;

    @Autowired
    private VouchersMapper vouchersMapper;

    @Mock
    private VouchersService vouchersServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVouchersMockMvc;

    private Vouchers vouchers;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vouchers createEntity(EntityManager em) {
        Vouchers vouchers = new Vouchers()
            .idVoucher(DEFAULT_ID_VOUCHER)
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .percentPromotion(DEFAULT_PERCENT_PROMOTION)
            .maxTotalMoneyPromotion(DEFAULT_MAX_TOTAL_MONEY_PROMOTION)
            .active(DEFAULT_ACTIVE)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return vouchers;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vouchers createUpdatedEntity(EntityManager em) {
        Vouchers vouchers = new Vouchers()
            .idVoucher(UPDATED_ID_VOUCHER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .percentPromotion(UPDATED_PERCENT_PROMOTION)
            .maxTotalMoneyPromotion(UPDATED_MAX_TOTAL_MONEY_PROMOTION)
            .active(UPDATED_ACTIVE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return vouchers;
    }

    @BeforeEach
    public void initTest() {
        vouchers = createEntity(em);
    }

    @Test
    @Transactional
    void createVouchers() throws Exception {
        int databaseSizeBeforeCreate = vouchersRepository.findAll().size();
        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);
        restVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isCreated());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeCreate + 1);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getIdVoucher()).isEqualTo(DEFAULT_ID_VOUCHER);
        assertThat(testVouchers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVouchers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVouchers.getPercentPromotion()).isEqualTo(DEFAULT_PERCENT_PROMOTION);
        assertThat(testVouchers.getMaxTotalMoneyPromotion()).isEqualTo(DEFAULT_MAX_TOTAL_MONEY_PROMOTION);
        assertThat(testVouchers.getActive()).isEqualTo(DEFAULT_ACTIVE);
        assertThat(testVouchers.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testVouchers.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testVouchers.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVouchers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testVouchers.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testVouchers.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createVouchersWithExistingId() throws Exception {
        // Create the Vouchers with an existing ID
        vouchers.setId(1L);
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        int databaseSizeBeforeCreate = vouchersRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdVoucherIsRequired() throws Exception {
        int databaseSizeBeforeTest = vouchersRepository.findAll().size();
        // set the field null
        vouchers.setIdVoucher(null);

        // Create the Vouchers, which fails.
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        restVouchersMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isBadRequest());

        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get all the vouchersList
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vouchers.getId().intValue())))
            .andExpect(jsonPath("$.[*].idVoucher").value(hasItem(DEFAULT_ID_VOUCHER)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].percentPromotion").value(hasItem(DEFAULT_PERCENT_PROMOTION.doubleValue())))
            .andExpect(jsonPath("$.[*].maxTotalMoneyPromotion").value(hasItem(DEFAULT_MAX_TOTAL_MONEY_PROMOTION.doubleValue())))
            .andExpect(jsonPath("$.[*].active").value(hasItem(DEFAULT_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.toString())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVouchersWithEagerRelationshipsIsEnabled() throws Exception {
        when(vouchersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVouchersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vouchersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllVouchersWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(vouchersServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restVouchersMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(vouchersServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    void getVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        // Get the vouchers
        restVouchersMockMvc
            .perform(get(ENTITY_API_URL_ID, vouchers.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(vouchers.getId().intValue()))
            .andExpect(jsonPath("$.idVoucher").value(DEFAULT_ID_VOUCHER))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.percentPromotion").value(DEFAULT_PERCENT_PROMOTION.doubleValue()))
            .andExpect(jsonPath("$.maxTotalMoneyPromotion").value(DEFAULT_MAX_TOTAL_MONEY_PROMOTION.doubleValue()))
            .andExpect(jsonPath("$.active").value(DEFAULT_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.toString()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingVouchers() throws Exception {
        // Get the vouchers
        restVouchersMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers
        Vouchers updatedVouchers = vouchersRepository.findById(vouchers.getId()).get();
        // Disconnect from session so that the updates on updatedVouchers are not directly saved in db
        em.detach(updatedVouchers);
        updatedVouchers
            .idVoucher(UPDATED_ID_VOUCHER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .percentPromotion(UPDATED_PERCENT_PROMOTION)
            .maxTotalMoneyPromotion(UPDATED_MAX_TOTAL_MONEY_PROMOTION)
            .active(UPDATED_ACTIVE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        VouchersDTO vouchersDTO = vouchersMapper.toDto(updatedVouchers);

        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getIdVoucher()).isEqualTo(UPDATED_ID_VOUCHER);
        assertThat(testVouchers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVouchers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVouchers.getPercentPromotion()).isEqualTo(UPDATED_PERCENT_PROMOTION);
        assertThat(testVouchers.getMaxTotalMoneyPromotion()).isEqualTo(UPDATED_MAX_TOTAL_MONEY_PROMOTION);
        assertThat(testVouchers.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVouchers.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testVouchers.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testVouchers.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVouchers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVouchers.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testVouchers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vouchersDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateVouchersWithPatch() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers using partial update
        Vouchers partialUpdatedVouchers = new Vouchers();
        partialUpdatedVouchers.setId(vouchers.getId());

        partialUpdatedVouchers.idVoucher(UPDATED_ID_VOUCHER).active(UPDATED_ACTIVE).endTime(UPDATED_END_TIME);

        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchers))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getIdVoucher()).isEqualTo(UPDATED_ID_VOUCHER);
        assertThat(testVouchers.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVouchers.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testVouchers.getPercentPromotion()).isEqualTo(DEFAULT_PERCENT_PROMOTION);
        assertThat(testVouchers.getMaxTotalMoneyPromotion()).isEqualTo(DEFAULT_MAX_TOTAL_MONEY_PROMOTION);
        assertThat(testVouchers.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVouchers.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testVouchers.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testVouchers.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testVouchers.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testVouchers.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testVouchers.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateVouchersWithPatch() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();

        // Update the vouchers using partial update
        Vouchers partialUpdatedVouchers = new Vouchers();
        partialUpdatedVouchers.setId(vouchers.getId());

        partialUpdatedVouchers
            .idVoucher(UPDATED_ID_VOUCHER)
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .percentPromotion(UPDATED_PERCENT_PROMOTION)
            .maxTotalMoneyPromotion(UPDATED_MAX_TOTAL_MONEY_PROMOTION)
            .active(UPDATED_ACTIVE)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedVouchers.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedVouchers))
            )
            .andExpect(status().isOk());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
        Vouchers testVouchers = vouchersList.get(vouchersList.size() - 1);
        assertThat(testVouchers.getIdVoucher()).isEqualTo(UPDATED_ID_VOUCHER);
        assertThat(testVouchers.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVouchers.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testVouchers.getPercentPromotion()).isEqualTo(UPDATED_PERCENT_PROMOTION);
        assertThat(testVouchers.getMaxTotalMoneyPromotion()).isEqualTo(UPDATED_MAX_TOTAL_MONEY_PROMOTION);
        assertThat(testVouchers.getActive()).isEqualTo(UPDATED_ACTIVE);
        assertThat(testVouchers.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testVouchers.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testVouchers.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testVouchers.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testVouchers.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testVouchers.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, vouchersDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamVouchers() throws Exception {
        int databaseSizeBeforeUpdate = vouchersRepository.findAll().size();
        vouchers.setId(count.incrementAndGet());

        // Create the Vouchers
        VouchersDTO vouchersDTO = vouchersMapper.toDto(vouchers);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restVouchersMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(vouchersDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Vouchers in the database
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteVouchers() throws Exception {
        // Initialize the database
        vouchersRepository.saveAndFlush(vouchers);

        int databaseSizeBeforeDelete = vouchersRepository.findAll().size();

        // Delete the vouchers
        restVouchersMockMvc
            .perform(delete(ENTITY_API_URL_ID, vouchers.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vouchers> vouchersList = vouchersRepository.findAll();
        assertThat(vouchersList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

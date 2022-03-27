package com.bkit12.app.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bkit12.app.IntegrationTest;
import com.bkit12.app.domain.Books;
import com.bkit12.app.domain.enumeration.CommonStatus;
import com.bkit12.app.repository.BooksRepository;
import com.bkit12.app.service.dto.BooksDTO;
import com.bkit12.app.service.mapper.BooksMapper;
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
 * Integration tests for the {@link BooksResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class BooksResourceIT {

    private static final String DEFAULT_ID_BOOK = "AAAAAAAAAA";
    private static final String UPDATED_ID_BOOK = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final CommonStatus DEFAULT_STATUS = CommonStatus.SOLD_OUT;
    private static final CommonStatus UPDATED_STATUS = CommonStatus.NEW;

    private static final String DEFAULT_DESCRIPTIOIN = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTIOIN = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CREATED_BY = 1L;
    private static final Long UPDATED_CREATED_BY = 2L;

    private static final Instant DEFAULT_UPDATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_UPDATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_UPDATED_BY = 1L;
    private static final Long UPDATED_UPDATED_BY = 2L;

    private static final String ENTITY_API_URL = "/api/books";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private BooksRepository booksRepository;

    @Autowired
    private BooksMapper booksMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBooksMockMvc;

    private Books books;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Books createEntity(EntityManager em) {
        Books books = new Books()
            .idBook(DEFAULT_ID_BOOK)
            .name(DEFAULT_NAME)
            .author(DEFAULT_AUTHOR)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .status(DEFAULT_STATUS)
            .descriptioin(DEFAULT_DESCRIPTIOIN)
            .createdDate(DEFAULT_CREATED_DATE)
            .createdBy(DEFAULT_CREATED_BY)
            .updatedDate(DEFAULT_UPDATED_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return books;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Books createUpdatedEntity(EntityManager em) {
        Books books = new Books()
            .idBook(UPDATED_ID_BOOK)
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .descriptioin(UPDATED_DESCRIPTIOIN)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return books;
    }

    @BeforeEach
    public void initTest() {
        books = createEntity(em);
    }

    @Test
    @Transactional
    void createBooks() throws Exception {
        int databaseSizeBeforeCreate = booksRepository.findAll().size();
        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);
        restBooksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isCreated());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeCreate + 1);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getIdBook()).isEqualTo(DEFAULT_ID_BOOK);
        assertThat(testBooks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBooks.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBooks.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBooks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBooks.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testBooks.getDescriptioin()).isEqualTo(DEFAULT_DESCRIPTIOIN);
        assertThat(testBooks.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBooks.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBooks.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testBooks.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void createBooksWithExistingId() throws Exception {
        // Create the Books with an existing ID
        books.setId(1L);
        BooksDTO booksDTO = booksMapper.toDto(books);

        int databaseSizeBeforeCreate = booksRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restBooksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkIdBookIsRequired() throws Exception {
        int databaseSizeBeforeTest = booksRepository.findAll().size();
        // set the field null
        books.setIdBook(null);

        // Create the Books, which fails.
        BooksDTO booksDTO = booksMapper.toDto(books);

        restBooksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isBadRequest());

        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = booksRepository.findAll().size();
        // set the field null
        books.setName(null);

        // Create the Books, which fails.
        BooksDTO booksDTO = booksMapper.toDto(books);

        restBooksMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isBadRequest());

        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        // Get all the booksList
        restBooksMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(books.getId().intValue())))
            .andExpect(jsonPath("$.[*].idBook").value(hasItem(DEFAULT_ID_BOOK)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].descriptioin").value(hasItem(DEFAULT_DESCRIPTIOIN)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.intValue())))
            .andExpect(jsonPath("$.[*].updatedDate").value(hasItem(DEFAULT_UPDATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY.intValue())));
    }

    @Test
    @Transactional
    void getBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        // Get the books
        restBooksMockMvc
            .perform(get(ENTITY_API_URL_ID, books.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(books.getId().intValue()))
            .andExpect(jsonPath("$.idBook").value(DEFAULT_ID_BOOK))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.descriptioin").value(DEFAULT_DESCRIPTIOIN))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.intValue()))
            .andExpect(jsonPath("$.updatedDate").value(DEFAULT_UPDATED_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingBooks() throws Exception {
        // Get the books
        restBooksMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Update the books
        Books updatedBooks = booksRepository.findById(books.getId()).get();
        // Disconnect from session so that the updates on updatedBooks are not directly saved in db
        em.detach(updatedBooks);
        updatedBooks
            .idBook(UPDATED_ID_BOOK)
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .descriptioin(UPDATED_DESCRIPTIOIN)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        BooksDTO booksDTO = booksMapper.toDto(updatedBooks);

        restBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, booksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booksDTO))
            )
            .andExpect(status().isOk());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getIdBook()).isEqualTo(UPDATED_ID_BOOK);
        assertThat(testBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBooks.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBooks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBooks.getDescriptioin()).isEqualTo(UPDATED_DESCRIPTIOIN);
        assertThat(testBooks.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBooks.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBooks.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testBooks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void putNonExistingBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, booksDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(booksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateBooksWithPatch() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Update the books using partial update
        Books partialUpdatedBooks = new Books();
        partialUpdatedBooks.setId(books.getId());

        partialUpdatedBooks.status(UPDATED_STATUS);

        restBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooks))
            )
            .andExpect(status().isOk());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getIdBook()).isEqualTo(DEFAULT_ID_BOOK);
        assertThat(testBooks.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testBooks.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testBooks.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testBooks.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testBooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBooks.getDescriptioin()).isEqualTo(DEFAULT_DESCRIPTIOIN);
        assertThat(testBooks.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testBooks.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testBooks.getUpdatedDate()).isEqualTo(DEFAULT_UPDATED_DATE);
        assertThat(testBooks.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    void fullUpdateBooksWithPatch() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        int databaseSizeBeforeUpdate = booksRepository.findAll().size();

        // Update the books using partial update
        Books partialUpdatedBooks = new Books();
        partialUpdatedBooks.setId(books.getId());

        partialUpdatedBooks
            .idBook(UPDATED_ID_BOOK)
            .name(UPDATED_NAME)
            .author(UPDATED_AUTHOR)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .status(UPDATED_STATUS)
            .descriptioin(UPDATED_DESCRIPTIOIN)
            .createdDate(UPDATED_CREATED_DATE)
            .createdBy(UPDATED_CREATED_BY)
            .updatedDate(UPDATED_UPDATED_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedBooks.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedBooks))
            )
            .andExpect(status().isOk());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
        Books testBooks = booksList.get(booksList.size() - 1);
        assertThat(testBooks.getIdBook()).isEqualTo(UPDATED_ID_BOOK);
        assertThat(testBooks.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testBooks.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testBooks.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testBooks.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testBooks.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testBooks.getDescriptioin()).isEqualTo(UPDATED_DESCRIPTIOIN);
        assertThat(testBooks.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testBooks.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testBooks.getUpdatedDate()).isEqualTo(UPDATED_UPDATED_DATE);
        assertThat(testBooks.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    void patchNonExistingBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, booksDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(booksDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamBooks() throws Exception {
        int databaseSizeBeforeUpdate = booksRepository.findAll().size();
        books.setId(count.incrementAndGet());

        // Create the Books
        BooksDTO booksDTO = booksMapper.toDto(books);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restBooksMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(booksDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Books in the database
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteBooks() throws Exception {
        // Initialize the database
        booksRepository.saveAndFlush(books);

        int databaseSizeBeforeDelete = booksRepository.findAll().size();

        // Delete the books
        restBooksMockMvc
            .perform(delete(ENTITY_API_URL_ID, books.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Books> booksList = booksRepository.findAll();
        assertThat(booksList).hasSize(databaseSizeBeforeDelete - 1);
    }
}

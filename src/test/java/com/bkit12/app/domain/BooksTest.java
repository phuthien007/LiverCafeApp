package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BooksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Books.class);
        Books books1 = new Books();
        books1.setId(1L);
        Books books2 = new Books();
        books2.setId(books1.getId());
        assertThat(books1).isEqualTo(books2);
        books2.setId(2L);
        assertThat(books1).isNotEqualTo(books2);
        books1.setId(null);
        assertThat(books1).isNotEqualTo(books2);
    }
}

package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foods.class);
        Foods foods1 = new Foods();
        foods1.setId(1L);
        Foods foods2 = new Foods();
        foods2.setId(foods1.getId());
        assertThat(foods1).isEqualTo(foods2);
        foods2.setId(2L);
        assertThat(foods1).isNotEqualTo(foods2);
        foods1.setId(null);
        assertThat(foods1).isNotEqualTo(foods2);
    }
}

package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrinksTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drinks.class);
        Drinks drinks1 = new Drinks();
        drinks1.setId(1L);
        Drinks drinks2 = new Drinks();
        drinks2.setId(drinks1.getId());
        assertThat(drinks1).isEqualTo(drinks2);
        drinks2.setId(2L);
        assertThat(drinks1).isNotEqualTo(drinks2);
        drinks1.setId(null);
        assertThat(drinks1).isNotEqualTo(drinks2);
    }
}

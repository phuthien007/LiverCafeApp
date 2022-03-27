package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrinksDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DrinksDTO.class);
        DrinksDTO drinksDTO1 = new DrinksDTO();
        drinksDTO1.setId(1L);
        DrinksDTO drinksDTO2 = new DrinksDTO();
        assertThat(drinksDTO1).isNotEqualTo(drinksDTO2);
        drinksDTO2.setId(drinksDTO1.getId());
        assertThat(drinksDTO1).isEqualTo(drinksDTO2);
        drinksDTO2.setId(2L);
        assertThat(drinksDTO1).isNotEqualTo(drinksDTO2);
        drinksDTO1.setId(null);
        assertThat(drinksDTO1).isNotEqualTo(drinksDTO2);
    }
}

package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FoodsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FoodsDTO.class);
        FoodsDTO foodsDTO1 = new FoodsDTO();
        foodsDTO1.setId(1L);
        FoodsDTO foodsDTO2 = new FoodsDTO();
        assertThat(foodsDTO1).isNotEqualTo(foodsDTO2);
        foodsDTO2.setId(foodsDTO1.getId());
        assertThat(foodsDTO1).isEqualTo(foodsDTO2);
        foodsDTO2.setId(2L);
        assertThat(foodsDTO1).isNotEqualTo(foodsDTO2);
        foodsDTO1.setId(null);
        assertThat(foodsDTO1).isNotEqualTo(foodsDTO2);
    }
}

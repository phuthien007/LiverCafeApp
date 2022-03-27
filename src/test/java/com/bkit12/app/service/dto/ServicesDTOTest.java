package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ServicesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServicesDTO.class);
        ServicesDTO servicesDTO1 = new ServicesDTO();
        servicesDTO1.setId(1L);
        ServicesDTO servicesDTO2 = new ServicesDTO();
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO2.setId(servicesDTO1.getId());
        assertThat(servicesDTO1).isEqualTo(servicesDTO2);
        servicesDTO2.setId(2L);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
        servicesDTO1.setId(null);
        assertThat(servicesDTO1).isNotEqualTo(servicesDTO2);
    }
}

package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VouchersDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(VouchersDTO.class);
        VouchersDTO vouchersDTO1 = new VouchersDTO();
        vouchersDTO1.setId(1L);
        VouchersDTO vouchersDTO2 = new VouchersDTO();
        assertThat(vouchersDTO1).isNotEqualTo(vouchersDTO2);
        vouchersDTO2.setId(vouchersDTO1.getId());
        assertThat(vouchersDTO1).isEqualTo(vouchersDTO2);
        vouchersDTO2.setId(2L);
        assertThat(vouchersDTO1).isNotEqualTo(vouchersDTO2);
        vouchersDTO1.setId(null);
        assertThat(vouchersDTO1).isNotEqualTo(vouchersDTO2);
    }
}

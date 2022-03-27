package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VouchersTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vouchers.class);
        Vouchers vouchers1 = new Vouchers();
        vouchers1.setId(1L);
        Vouchers vouchers2 = new Vouchers();
        vouchers2.setId(vouchers1.getId());
        assertThat(vouchers1).isEqualTo(vouchers2);
        vouchers2.setId(2L);
        assertThat(vouchers1).isNotEqualTo(vouchers2);
        vouchers1.setId(null);
        assertThat(vouchers1).isNotEqualTo(vouchers2);
    }
}

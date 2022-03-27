package com.bkit12.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PaymentsMapperTest {

    private PaymentsMapper paymentsMapper;

    @BeforeEach
    public void setUp() {
        paymentsMapper = new PaymentsMapperImpl();
    }
}

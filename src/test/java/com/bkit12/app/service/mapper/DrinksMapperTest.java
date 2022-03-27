package com.bkit12.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrinksMapperTest {

    private DrinksMapper drinksMapper;

    @BeforeEach
    public void setUp() {
        drinksMapper = new DrinksMapperImpl();
    }
}

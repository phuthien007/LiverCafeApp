package com.bkit12.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkingSpaceFormsMapperTest {

    private WorkingSpaceFormsMapper workingSpaceFormsMapper;

    @BeforeEach
    public void setUp() {
        workingSpaceFormsMapper = new WorkingSpaceFormsMapperImpl();
    }
}

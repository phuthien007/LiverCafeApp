package com.bkit12.app.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkingSpacesMapperTest {

    private WorkingSpacesMapper workingSpacesMapper;

    @BeforeEach
    public void setUp() {
        workingSpacesMapper = new WorkingSpacesMapperImpl();
    }
}

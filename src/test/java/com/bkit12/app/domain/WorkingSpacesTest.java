package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingSpacesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingSpaces.class);
        WorkingSpaces workingSpaces1 = new WorkingSpaces();
        workingSpaces1.setId(1L);
        WorkingSpaces workingSpaces2 = new WorkingSpaces();
        workingSpaces2.setId(workingSpaces1.getId());
        assertThat(workingSpaces1).isEqualTo(workingSpaces2);
        workingSpaces2.setId(2L);
        assertThat(workingSpaces1).isNotEqualTo(workingSpaces2);
        workingSpaces1.setId(null);
        assertThat(workingSpaces1).isNotEqualTo(workingSpaces2);
    }
}

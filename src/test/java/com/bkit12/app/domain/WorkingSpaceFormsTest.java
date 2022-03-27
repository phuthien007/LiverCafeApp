package com.bkit12.app.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingSpaceFormsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingSpaceForms.class);
        WorkingSpaceForms workingSpaceForms1 = new WorkingSpaceForms();
        workingSpaceForms1.setId(1L);
        WorkingSpaceForms workingSpaceForms2 = new WorkingSpaceForms();
        workingSpaceForms2.setId(workingSpaceForms1.getId());
        assertThat(workingSpaceForms1).isEqualTo(workingSpaceForms2);
        workingSpaceForms2.setId(2L);
        assertThat(workingSpaceForms1).isNotEqualTo(workingSpaceForms2);
        workingSpaceForms1.setId(null);
        assertThat(workingSpaceForms1).isNotEqualTo(workingSpaceForms2);
    }
}

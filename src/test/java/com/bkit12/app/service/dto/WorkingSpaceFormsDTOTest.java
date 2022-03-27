package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingSpaceFormsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingSpaceFormsDTO.class);
        WorkingSpaceFormsDTO workingSpaceFormsDTO1 = new WorkingSpaceFormsDTO();
        workingSpaceFormsDTO1.setId(1L);
        WorkingSpaceFormsDTO workingSpaceFormsDTO2 = new WorkingSpaceFormsDTO();
        assertThat(workingSpaceFormsDTO1).isNotEqualTo(workingSpaceFormsDTO2);
        workingSpaceFormsDTO2.setId(workingSpaceFormsDTO1.getId());
        assertThat(workingSpaceFormsDTO1).isEqualTo(workingSpaceFormsDTO2);
        workingSpaceFormsDTO2.setId(2L);
        assertThat(workingSpaceFormsDTO1).isNotEqualTo(workingSpaceFormsDTO2);
        workingSpaceFormsDTO1.setId(null);
        assertThat(workingSpaceFormsDTO1).isNotEqualTo(workingSpaceFormsDTO2);
    }
}

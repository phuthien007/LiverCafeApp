package com.bkit12.app.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.bkit12.app.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WorkingSpacesDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WorkingSpacesDTO.class);
        WorkingSpacesDTO workingSpacesDTO1 = new WorkingSpacesDTO();
        workingSpacesDTO1.setId(1L);
        WorkingSpacesDTO workingSpacesDTO2 = new WorkingSpacesDTO();
        assertThat(workingSpacesDTO1).isNotEqualTo(workingSpacesDTO2);
        workingSpacesDTO2.setId(workingSpacesDTO1.getId());
        assertThat(workingSpacesDTO1).isEqualTo(workingSpacesDTO2);
        workingSpacesDTO2.setId(2L);
        assertThat(workingSpacesDTO1).isNotEqualTo(workingSpacesDTO2);
        workingSpacesDTO1.setId(null);
        assertThat(workingSpacesDTO1).isNotEqualTo(workingSpacesDTO2);
    }
}

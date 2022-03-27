package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaceForms;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WorkingSpaceFormsRepositoryWithBagRelationships {
    Optional<WorkingSpaceForms> fetchBagRelationships(Optional<WorkingSpaceForms> workingSpaceForms);

    List<WorkingSpaceForms> fetchBagRelationships(List<WorkingSpaceForms> workingSpaceForms);

    Page<WorkingSpaceForms> fetchBagRelationships(Page<WorkingSpaceForms> workingSpaceForms);
}

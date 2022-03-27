package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaces;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface WorkingSpacesRepositoryWithBagRelationships {
    Optional<WorkingSpaces> fetchBagRelationships(Optional<WorkingSpaces> workingSpaces);

    List<WorkingSpaces> fetchBagRelationships(List<WorkingSpaces> workingSpaces);

    Page<WorkingSpaces> fetchBagRelationships(Page<WorkingSpaces> workingSpaces);
}

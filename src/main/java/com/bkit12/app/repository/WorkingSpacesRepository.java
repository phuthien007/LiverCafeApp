package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaces;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkingSpaces entity.
 */
@Repository
public interface WorkingSpacesRepository extends WorkingSpacesRepositoryWithBagRelationships, JpaRepository<WorkingSpaces, Long> {
    default Optional<WorkingSpaces> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<WorkingSpaces> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<WorkingSpaces> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}

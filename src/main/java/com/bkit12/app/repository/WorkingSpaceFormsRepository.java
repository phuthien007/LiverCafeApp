package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaceForms;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the WorkingSpaceForms entity.
 */
@Repository
public interface WorkingSpaceFormsRepository
    extends WorkingSpaceFormsRepositoryWithBagRelationships, JpaRepository<WorkingSpaceForms, Long> {
    default Optional<WorkingSpaceForms> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<WorkingSpaceForms> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<WorkingSpaceForms> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}

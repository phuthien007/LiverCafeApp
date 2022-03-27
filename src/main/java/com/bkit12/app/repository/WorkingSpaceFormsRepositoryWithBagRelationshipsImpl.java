package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaceForms;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.hibernate.annotations.QueryHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class WorkingSpaceFormsRepositoryWithBagRelationshipsImpl implements WorkingSpaceFormsRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<WorkingSpaceForms> fetchBagRelationships(Optional<WorkingSpaceForms> workingSpaceForms) {
        return workingSpaceForms.map(this::fetchServices);
    }

    @Override
    public Page<WorkingSpaceForms> fetchBagRelationships(Page<WorkingSpaceForms> workingSpaceForms) {
        return new PageImpl<>(
            fetchBagRelationships(workingSpaceForms.getContent()),
            workingSpaceForms.getPageable(),
            workingSpaceForms.getTotalElements()
        );
    }

    @Override
    public List<WorkingSpaceForms> fetchBagRelationships(List<WorkingSpaceForms> workingSpaceForms) {
        return Optional.of(workingSpaceForms).map(this::fetchServices).get();
    }

    WorkingSpaceForms fetchServices(WorkingSpaceForms result) {
        return entityManager
            .createQuery(
                "select workingSpaceForms from WorkingSpaceForms workingSpaceForms left join fetch workingSpaceForms.services where workingSpaceForms is :workingSpaceForms",
                WorkingSpaceForms.class
            )
            .setParameter("workingSpaceForms", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<WorkingSpaceForms> fetchServices(List<WorkingSpaceForms> workingSpaceForms) {
        return entityManager
            .createQuery(
                "select distinct workingSpaceForms from WorkingSpaceForms workingSpaceForms left join fetch workingSpaceForms.services where workingSpaceForms in :workingSpaceForms",
                WorkingSpaceForms.class
            )
            .setParameter("workingSpaceForms", workingSpaceForms)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}

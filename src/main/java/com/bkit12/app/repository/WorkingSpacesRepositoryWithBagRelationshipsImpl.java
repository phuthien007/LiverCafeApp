package com.bkit12.app.repository;

import com.bkit12.app.domain.WorkingSpaces;
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
public class WorkingSpacesRepositoryWithBagRelationshipsImpl implements WorkingSpacesRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<WorkingSpaces> fetchBagRelationships(Optional<WorkingSpaces> workingSpaces) {
        return workingSpaces.map(this::fetchWorkingSpaceForms);
    }

    @Override
    public Page<WorkingSpaces> fetchBagRelationships(Page<WorkingSpaces> workingSpaces) {
        return new PageImpl<>(
            fetchBagRelationships(workingSpaces.getContent()),
            workingSpaces.getPageable(),
            workingSpaces.getTotalElements()
        );
    }

    @Override
    public List<WorkingSpaces> fetchBagRelationships(List<WorkingSpaces> workingSpaces) {
        return Optional.of(workingSpaces).map(this::fetchWorkingSpaceForms).get();
    }

    WorkingSpaces fetchWorkingSpaceForms(WorkingSpaces result) {
        return entityManager
            .createQuery(
                "select workingSpaces from WorkingSpaces workingSpaces left join fetch workingSpaces.workingSpaceForms where workingSpaces is :workingSpaces",
                WorkingSpaces.class
            )
            .setParameter("workingSpaces", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<WorkingSpaces> fetchWorkingSpaceForms(List<WorkingSpaces> workingSpaces) {
        return entityManager
            .createQuery(
                "select distinct workingSpaces from WorkingSpaces workingSpaces left join fetch workingSpaces.workingSpaceForms where workingSpaces in :workingSpaces",
                WorkingSpaces.class
            )
            .setParameter("workingSpaces", workingSpaces)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}

package com.bkit12.app.repository;

import com.bkit12.app.domain.Vouchers;
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
public class VouchersRepositoryWithBagRelationshipsImpl implements VouchersRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<Vouchers> fetchBagRelationships(Optional<Vouchers> vouchers) {
        return vouchers.map(this::fetchCustomers);
    }

    @Override
    public Page<Vouchers> fetchBagRelationships(Page<Vouchers> vouchers) {
        return new PageImpl<>(fetchBagRelationships(vouchers.getContent()), vouchers.getPageable(), vouchers.getTotalElements());
    }

    @Override
    public List<Vouchers> fetchBagRelationships(List<Vouchers> vouchers) {
        return Optional.of(vouchers).map(this::fetchCustomers).get();
    }

    Vouchers fetchCustomers(Vouchers result) {
        return entityManager
            .createQuery(
                "select vouchers from Vouchers vouchers left join fetch vouchers.customers where vouchers is :vouchers",
                Vouchers.class
            )
            .setParameter("vouchers", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Vouchers> fetchCustomers(List<Vouchers> vouchers) {
        return entityManager
            .createQuery(
                "select distinct vouchers from Vouchers vouchers left join fetch vouchers.customers where vouchers in :vouchers",
                Vouchers.class
            )
            .setParameter("vouchers", vouchers)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}

package com.bkit12.app.repository;

import com.bkit12.app.domain.OrderDetails;
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
public class OrderDetailsRepositoryWithBagRelationshipsImpl implements OrderDetailsRepositoryWithBagRelationships {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Optional<OrderDetails> fetchBagRelationships(Optional<OrderDetails> orderDetails) {
        return orderDetails.map(this::fetchDrinks).map(this::fetchBooks).map(this::fetchFoods);
    }

    @Override
    public Page<OrderDetails> fetchBagRelationships(Page<OrderDetails> orderDetails) {
        return new PageImpl<>(
            fetchBagRelationships(orderDetails.getContent()),
            orderDetails.getPageable(),
            orderDetails.getTotalElements()
        );
    }

    @Override
    public List<OrderDetails> fetchBagRelationships(List<OrderDetails> orderDetails) {
        return Optional.of(orderDetails).map(this::fetchDrinks).map(this::fetchBooks).map(this::fetchFoods).get();
    }

    OrderDetails fetchDrinks(OrderDetails result) {
        return entityManager
            .createQuery(
                "select orderDetails from OrderDetails orderDetails left join fetch orderDetails.drinks where orderDetails is :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<OrderDetails> fetchDrinks(List<OrderDetails> orderDetails) {
        return entityManager
            .createQuery(
                "select distinct orderDetails from OrderDetails orderDetails left join fetch orderDetails.drinks where orderDetails in :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", orderDetails)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    OrderDetails fetchBooks(OrderDetails result) {
        return entityManager
            .createQuery(
                "select orderDetails from OrderDetails orderDetails left join fetch orderDetails.books where orderDetails is :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<OrderDetails> fetchBooks(List<OrderDetails> orderDetails) {
        return entityManager
            .createQuery(
                "select distinct orderDetails from OrderDetails orderDetails left join fetch orderDetails.books where orderDetails in :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", orderDetails)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }

    OrderDetails fetchFoods(OrderDetails result) {
        return entityManager
            .createQuery(
                "select orderDetails from OrderDetails orderDetails left join fetch orderDetails.foods where orderDetails is :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<OrderDetails> fetchFoods(List<OrderDetails> orderDetails) {
        return entityManager
            .createQuery(
                "select distinct orderDetails from OrderDetails orderDetails left join fetch orderDetails.foods where orderDetails in :orderDetails",
                OrderDetails.class
            )
            .setParameter("orderDetails", orderDetails)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}

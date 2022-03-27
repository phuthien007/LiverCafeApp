package com.bkit12.app.repository;

import com.bkit12.app.domain.Drinks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Drinks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrinksRepository extends JpaRepository<Drinks, Long> {}

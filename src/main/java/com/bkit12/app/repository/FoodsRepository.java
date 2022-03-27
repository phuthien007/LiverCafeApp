package com.bkit12.app.repository;

import com.bkit12.app.domain.Foods;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Foods entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FoodsRepository extends JpaRepository<Foods, Long> {}

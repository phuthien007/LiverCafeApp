package com.bkit12.app.repository;

import com.bkit12.app.domain.Users;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Users entity.
 */
@Repository
public interface UsersRepository extends UsersRepositoryWithBagRelationships, JpaRepository<Users, Long> {
    default Optional<Users> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Users> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Users> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }

    @EntityGraph(attributePaths = "roles")
    Optional<Users> findOneByUsername(String username);

    @EntityGraph(attributePaths = "roles")
    Optional<Users> findOneByEmail(String email);



}

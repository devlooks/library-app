package com.group.libraryapp.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// <Entity 명, pk 컬럼>
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    boolean existsByName(String name);

            
}

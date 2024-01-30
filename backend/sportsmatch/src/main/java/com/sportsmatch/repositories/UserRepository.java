package com.sportsmatch.repositories;

import com.sportsmatch.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserById(Long id);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}

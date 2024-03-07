package com.sportsmatch.repositories;

import com.sportsmatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Transactional
  Optional<User> findUserById(Long id);

  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}

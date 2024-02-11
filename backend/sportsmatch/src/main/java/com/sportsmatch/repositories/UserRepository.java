package com.sportsmatch.repositories;

import com.sportsmatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findUserById(Long id);

  Optional<User> findByEmail(String email);

  /**
   * This query return an user by the given name
   * @param name this paramater filter it out
   * @return an user with the given name
   */
  @Query("SELECT us FROM User us WHERE us.name = :name")
  Optional<User> getUserByName(@Param("name") String name);

  boolean existsByEmail(String email);
}

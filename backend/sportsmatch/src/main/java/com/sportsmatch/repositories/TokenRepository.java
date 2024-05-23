package com.sportsmatch.repositories;

import com.sportsmatch.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {

  boolean existsByToken(String token);
}

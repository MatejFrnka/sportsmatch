package com.sportsmatch.repos;

import com.sportsmatch.models.EventPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long> {
}

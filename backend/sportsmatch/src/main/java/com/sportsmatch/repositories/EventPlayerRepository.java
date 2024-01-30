package com.sportsmatch.repositories;

import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long> {

  EventPlayer findEventPlayerById(Long id);

  EventPlayer findEventPlayerByPlayer(User player);
}

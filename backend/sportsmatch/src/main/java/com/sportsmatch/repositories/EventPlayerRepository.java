package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long> {

  EventPlayer findEventPlayerById(Long id);

  Optional<EventPlayer> findEventPlayerByPlayer(User player);

  List<EventPlayer> findEventPlayersByEvent(Event event);
}

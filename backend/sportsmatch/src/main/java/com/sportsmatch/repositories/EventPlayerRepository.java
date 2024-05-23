package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventPlayerRepository extends JpaRepository<EventPlayer, Long> {

  Optional<EventPlayer> findEventPlayerByPlayerAndEventId(User player, Long eventId);

  List<EventPlayer> findEventPlayersByEvent(Event event);

  Optional<EventPlayer> findEventPlayerByEventAndPlayer(Event event, User player);

  @Query(
      "SELECT ep.event FROM EventPlayer ep JOIN Event e ON e.id = ep.event.id WHERE ep.player.id = :id AND e.dateEnd < :now")
  List<Event> findPastEventsByPlayer(@Param("id") Long id, @Param("now") LocalDateTime now);
}

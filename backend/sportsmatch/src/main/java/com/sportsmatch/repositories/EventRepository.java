package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  Optional<Event> findEventById(Long id);

  List<Event> findAllBySportIdIn(List<Long> sportId);

  /**
   * Retrieves events filtered by user and finished status.
   *
   * @param id the logged user's id to filter events by
   * @param now the current time to filter events by
   * @param pageable pagination information (page, size)
   * @return a list of events filtered by user and finished status
   */
  // =?1 =?2")
  @Query("SELECT ep.event FROM EventPlayer ep WHERE ep.player.id = :id AND ep.event.dateEnd < :now")
  List<Event> findEventsByUser(
      @Param("id") Long id, @Param("now") LocalDateTime now, Pageable pageable);

  @Query("SELECT ep.event FROM EventPlayer ep WHERE ep.player.id = :id AND ep.event.dateEnd > :now ORDER BY ep.event.dateEnd ASC")
  List<Event> findUpcomingEventsByUser(@Param("id") Long id, @Param("now") LocalDateTime now);
}

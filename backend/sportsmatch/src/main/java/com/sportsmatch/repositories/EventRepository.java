package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
   * @param user     the user to filter events by
   * @param now      the current time to filter events by
   * @param pageable pagination information (page, size)
   * @return a list of events filtered by user and finished status
   */
  @Query("SELECT e FROM Event e JOIN e.players ep WHERE ep.player = ?1 AND e.dateEnd <= ?2")
  List<Event> findEventsByUser(User user, LocalDateTime now, Pageable pageable);

}

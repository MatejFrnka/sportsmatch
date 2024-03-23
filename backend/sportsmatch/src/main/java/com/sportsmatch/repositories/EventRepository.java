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
   * @param name     the logged user's name to filter events by
   * @param now      the current time to filter events by
   * @param pageable pagination information (page, size)
   * @return a list of events filtered by user and finished status
   */
  // =?1 =?2")
  @Query("SELECT ep.event FROM EventPlayer ep WHERE ep.player.name = :name AND ep.event.dateEnd < :now")
  List<Event> findEventsByUser(
      @Param("name") String name,
      @Param("now") LocalDateTime now,
      Pageable pageable
  );

  //TODO: Finish the code
  @Query("SELECT e.id AS event_id, e.title AS event_name, p.latitude AS event_location_latitude, " +
      "p.longitude AS event_location_longitude, s.name AS sport_name, " +
      "SQRT(POW(p.latitude - :userLatitude, 2) + POW(p.longitude - :userLongitude, 2)) AS distance " +
      "FROM Event e JOIN e.sport s JOIN e.place p " +
      "WHERE (:sport IS NULL OR s.name IN :sport) " +
      "ORDER BY SQRT(POW(p.latitude - :userLatitude, 2) + POW(p.longitude - :userLongitude, 2))")
  List<Event> nearByEvents(@Param("userLatitude") Double userLatitude,
                           @Param("userLongitude") Double userLongitude,
                           @Param("sport") List<String> sport,
                           Pageable pageable);
}

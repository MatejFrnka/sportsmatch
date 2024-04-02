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


  @Query(nativeQuery = true, value =
      "SELECT e.id, e.date_start, e.date_end, e.min_elo, e.max_elo, e.title, e.sport_id, e.place_id " +
      "FROM events e " +
      "JOIN sports s ON e.sport_id = s.id " +
      "JOIN places p ON e.place_id = p.id " +
      "WHERE s.name IN :sport_name " +
      "ORDER BY ( " +
      "      6371 * acos( " +     // Haversine distance calculation
      "        cos(radians(p.latitude)) * cos(radians(:latitude)) * " +
      "        cos(radians(:longitude) - radians(p.longitude)) + " +
      "        sin(radians(p.latitude)) * sin(radians(:latitude)))) ASC;")
  List<Event> findNearbyEvents(@Param("longitude") final double userLongitude,
                               @Param("latitude") final double userLatitude,
                               @Param("sport_name") final List<String> sportNames,
                               Pageable pageable);
}

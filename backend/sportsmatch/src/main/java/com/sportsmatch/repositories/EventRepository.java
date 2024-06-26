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

  @Query(
      "SELECT ep.event FROM EventPlayer ep WHERE ep.player.id = :id AND ep.event.dateStart > :now ORDER BY ep.event.dateEnd ASC")
  List<Event> findUpcomingEventsByUser(@Param("id") Long id, @Param("now") LocalDateTime now);

  /**
   * Finds events near the user location, optionally filtered by sport names.
   *
   * <p>This method uses a native SQL query with a Haversine distance calculation to find events
   * ordered by their distance from the user's location.
   *
   * @param userLongitude user's longitude coordinate
   * @param userLatitude user's latitude coordinate
   * @param sportNames optional, list of sport names to filter events by. If null, all events are
   *     returned. Sport names are not case-sensitive.
   * @param placeName optional, substring to search name of the place by. If null all places are
   *     returned. It is not case-sensitive.
   * @param now date and time to search events by. Only events starting after this date and time are
   *     returned.
   * @param pageable contains the page and size
   * @return list of events filtered by sport names if given, and order by distance from the user's
   *     given location
   */
  @Query(
      nativeQuery = true,
      value =
          "SELECT e.id, e.date_start, e.date_end, e.min_elo, e.max_elo, e.title, e.is_rank_updated, e.sport_id, e.place_id "
              + "FROM events e "
              + "JOIN sports s ON e.sport_id = s.id "
              + "JOIN places p ON e.place_id = p.id "
              + "WHERE (LOWER(p.name) LIKE %:placeName%) AND (:sportNames IS NULL OR LOWER(s.name) IN(:sportNames)) AND (e.date_start > :now)"
              + "ORDER BY ( "
              + "      6371 * acos( " // Haversine distance calculation
              + "        cos(radians(p.latitude)) * cos(radians(:latitude)) * "
              + "        cos(radians(:longitude) - radians(p.longitude)) + "
              + "        sin(radians(p.latitude)) * sin(radians(:latitude))) "
              + "    ) ASC;")
  List<Event> findNearbyEvents(
      @Param("longitude") final double userLongitude,
      @Param("latitude") final double userLatitude,
      @Param("sportNames") final List<String> sportNames,
      @Param("placeName") final String placeName,
      @Param("now") final LocalDateTime now,
      Pageable pageable);
}

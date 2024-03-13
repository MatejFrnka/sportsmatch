package com.sportsmatch.repositories;

import com.sportsmatch.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

  /**
   * Search places by the given name. The search is not case-sensitive.
   *
   * @param name of the place to filter by. Set null to ignore this filter.
   * @return a list of Place entity that match the specified criteria.
   */
  @Query("SELECT p FROM Place p WHERE (:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%')))")
  List<Place> searchPlaces(String name);
}

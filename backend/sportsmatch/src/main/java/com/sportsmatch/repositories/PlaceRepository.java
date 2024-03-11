package com.sportsmatch.repositories;

import com.sportsmatch.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long> {

  /**
   * Searches places based on provided name.
   *
   * @param name of the Places to search for.
   * @return a list of Places whose name match the provided name.
   */
  List<Place> findByName(String name);
}

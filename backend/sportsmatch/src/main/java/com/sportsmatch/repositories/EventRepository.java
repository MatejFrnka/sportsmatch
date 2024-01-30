package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;

import java.util.List;
import java.util.Optional;

import com.sportsmatch.models.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

  Optional<Event> findEventById(Long id);

  List<Event> findAllBySportIdIn(List<Long> sport_id);
}

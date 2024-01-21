package com.sportsmatch.repos;

import com.sportsmatch.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    public Event findEventById(Long id);
}

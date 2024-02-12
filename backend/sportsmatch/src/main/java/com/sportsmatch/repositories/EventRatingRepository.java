package com.sportsmatch.repositories;

import com.sportsmatch.models.EventRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRatingRepository extends JpaRepository<EventRating, Long> {
}

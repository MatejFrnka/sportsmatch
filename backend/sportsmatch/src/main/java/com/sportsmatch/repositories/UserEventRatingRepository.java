package com.sportsmatch.repositories;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.User;
import com.sportsmatch.models.UserEventRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEventRatingRepository extends JpaRepository<UserEventRating, Long> {
  Optional<UserEventRating> findUserEventRatingByEventAndPlayer(Event event, User player);
}

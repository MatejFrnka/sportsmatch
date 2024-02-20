package com.sportsmatch.repositories;

import com.sportsmatch.models.UserEventRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEventRatingRepository extends JpaRepository<UserEventRating, Long> {
}

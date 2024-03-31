package com.sportsmatch.repositories;

import com.sportsmatch.dtos.UserRatingDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.User;
import com.sportsmatch.models.UserEventRating;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserEventRatingRepository extends JpaRepository<UserEventRating, Long> {
  Optional<UserEventRating> findUserEventRatingByEventAndPlayer(Event event, User player);

  @Query(
      "SELECT r.starRating, COUNT(r.starRating) FROM UserEventRating uer JOIN Rating r ON uer.userRating.id = r.id "
          + "WHERE uer.opponent.id = :id GROUP BY r.starRating")
  List<Object[]> findRatingsCount(@Param("id") Long id);

  @Query(
      "SELECT AVG(r.starRating) FROM UserEventRating uer JOIN Rating r ON uer.userRating.id = r.id WHERE uer.opponent.id = :id")
  Double findAverageRating(@Param("id") Long id);

  @Query(
      "SELECT new com.sportsmatch.dtos.UserRatingDTO(uer.player.name, r.textRating, r.starRating, r.createdAt) FROM UserEventRating uer JOIN Rating r "
          + "ON uer.userRating.id = r.id WHERE uer.opponent.id = :id ORDER BY r.createdAt DESC")
  List<UserRatingDTO> findAllByOpponent(@Param("id") Long id, Pageable pageable);
}

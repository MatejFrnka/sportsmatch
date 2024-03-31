package com.sportsmatch.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class UserRatingDTO {

  private String opponentName;
  private String userTextRating;
  private Integer userStarRating;
  private LocalDateTime createdAt;

  public UserRatingDTO(
      String opponentName, String userTextRating, Integer userStarRating, LocalDateTime createdAt) {
    this.opponentName = opponentName;
    this.userTextRating = userTextRating;
    this.userStarRating = userStarRating;
    this.createdAt = createdAt;
  }
}

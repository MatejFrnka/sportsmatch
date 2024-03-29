package com.sportsmatch.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserStarRatingCountDTO {
  private Integer starRating;
  private Long starRatingCount;

  public UserStarRatingCountDTO(Integer starRating, Long starRatingCount) {
    this.starRating = starRating;
    this.starRatingCount = starRatingCount;
  }
}

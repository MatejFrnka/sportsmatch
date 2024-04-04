package com.sportsmatch.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class UserRatingStatsDTO {
  private Map<String, Integer> starRatingCounts;
  private Double averageRating;

  public UserRatingStatsDTO() {
    starRatingCounts = initializeStarRatingCounts();
    averageRating = 0.0;
  }

  private Map<String, Integer> initializeStarRatingCounts() {
    Map<String, Integer> result = new HashMap<>();
    for (int i = 1; i <= 5; i++) {
      result.put(String.valueOf(i), 0);
    }
    return result;
  }
}

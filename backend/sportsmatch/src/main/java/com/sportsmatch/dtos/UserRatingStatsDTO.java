package com.sportsmatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRatingStatsDTO {
  private List<UserStarRatingCountDTO> starRatingCounts;
  private Double averageRating;
}

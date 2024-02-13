package com.sportsmatch.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RatingDTO {

  private String userTextRating;

  @NotNull
  @Min(0)
  @Max(5)
  private Integer userStarRating;

  @NotNull
  @Min(0)
  @Max(5)
  private Integer eventStarRating;

  @NotNull
  @Min(0)
  private Integer myScore;

  @NotNull
  @Min(0)
  private Integer opponentScore;
}

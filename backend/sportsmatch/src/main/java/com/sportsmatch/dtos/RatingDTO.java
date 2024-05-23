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

  @NotNull(message = "User star cannot be null")
  @Min(value = 0, message = "User star rating must be at least 0")
  @Max(value = 5, message = "User star rating must be at most 5")
  private Integer userStarRating;

  @NotNull(message = "Event star cannot be null")
  @Min(value = 0, message = "Event star rating must be at least 0")
  @Max(value = 5, message = "Event star rating must be at most 5")
  private Integer eventStarRating;

  @NotNull(message = "My score cannot be null")
  @Min(value = 0, message = "My score must be at least 0")
  private Integer myScore;

  @NotNull(message = "Opponent score cannot be null")
  @Min(value = 0, message = "Opponent score must be at least 0")
  private Integer opponentScore;

  private Long eventId;
}

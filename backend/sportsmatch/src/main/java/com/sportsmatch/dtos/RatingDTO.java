package com.sportsmatch.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    @NotNull private Integer userStarRating;
    @NotNull private Integer eventStarRating;
    @NotNull private Integer myScore;
    @NotNull private Integer opponentScore;
}

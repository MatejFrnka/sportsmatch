package com.sportsmatch.dtos;

import com.sportsmatch.models.EventStatusOptions;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventHistoryDTO {

  private Integer userScore;
  private Integer opponentScore;
  private UserDTO opponent;
  private LocalDateTime dateOfTheMatch;
  private EventStatusOptions status;

}

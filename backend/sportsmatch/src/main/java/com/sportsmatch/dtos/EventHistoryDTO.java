package com.sportsmatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventHistoryDTO {

  private Integer userScore;
  private Integer opponentScore;
  private UserDTO opponent;
}

package com.sportsmatch.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {

  private Long id;

  @NotNull
  private LocalDateTime dateStart;
  @NotNull
  private LocalDateTime dateEnd;
  @NotNull
  private Integer minElo;
  @NotNull
  private Integer maxElo;
  @NotBlank
  private String title;

  private Long player1Id;
  private Long player2Id;
  @NotBlank private String sport;

  private String player1Name;
  private String player2Name;

  @NotNull
  private PlaceDTO placeDTO;
}

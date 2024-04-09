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
public class HostEventDTO {

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

  @NotBlank
  private String sport;
  @NotNull
  private PlaceDTO placeDTO;
}

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
  @NotBlank
  private String sport;
  @NotNull
  private Long locationId;
}

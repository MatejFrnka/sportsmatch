package com.sportsmatch.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEventDTO {

  private List<String> sportNames;

  @NotBlank
  private double longitude;

  @NotBlank
  private double latitude;
}

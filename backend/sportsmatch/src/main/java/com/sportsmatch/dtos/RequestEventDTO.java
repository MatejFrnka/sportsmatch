package com.sportsmatch.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class RequestEventDTO {

  private List<String> sportNames;

  @NotBlank(message = "Longitude is required")
  private double longitude;

  @NotBlank(message = "Latitude is required")
  private double latitude;
}

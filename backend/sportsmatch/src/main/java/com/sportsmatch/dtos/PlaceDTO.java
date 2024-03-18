package com.sportsmatch.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlaceDTO {

  @NotBlank
  private String name;

  @NotBlank
  private String address;

  @NotNull
  private Float latitude;

  @NotNull
  private Float longitude;
}

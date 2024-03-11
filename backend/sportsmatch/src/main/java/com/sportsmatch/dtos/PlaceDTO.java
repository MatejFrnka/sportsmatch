package com.sportsmatch.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
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

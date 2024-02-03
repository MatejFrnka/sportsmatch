package com.sportsmatch.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SportDTO {

  public String name;

  public SportDTO(String name) {
    this.name = name;
  }
}
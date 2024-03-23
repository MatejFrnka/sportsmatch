package com.sportsmatch.dtos;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEventDTO {

  private List<String> sportNames;

  @NonNull
  private Double longitude;

  @NonNull
  private Double latitude;
}

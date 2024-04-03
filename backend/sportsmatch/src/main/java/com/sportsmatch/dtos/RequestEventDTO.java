package com.sportsmatch.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestEventDTO {

  private List<String> sportNames = new ArrayList<>(); // must initialize for native query

  private double longitude;

  private double latitude;
}

package com.sportsmatch.dtos;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private String name;
  private List<SportDTO> sports;
  private Integer elo;
}

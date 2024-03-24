package com.sportsmatch.dtos;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private String name;
  private Integer elo;
  private List<SportDTO> sports;
}

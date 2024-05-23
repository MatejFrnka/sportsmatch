package com.sportsmatch.dtos;

import java.util.List;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

  private Long id;
  private String name;
  private Integer elo;
  private Integer win;
  private Integer loss;
  private List<SportDTO> sports;
}

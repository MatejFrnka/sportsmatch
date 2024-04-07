package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token {

  @Id
  @GeneratedValue
  private Long id;

  private String token;
  private Boolean isValid;

  @Enumerated(EnumType.STRING)
  private TokenType tokenType;

  @ManyToOne private User user;
}

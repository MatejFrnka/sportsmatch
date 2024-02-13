package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "text_rating")
  private String textRating;
  @Column(name = "star_rating")
  private Integer starRating;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRating", fetch = FetchType.EAGER)
  Set<UserEventRating> ratings = new HashSet<>();
}

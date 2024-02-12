package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name = "star_rating")
  private Integer starRating;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventRating")
  Set<UserEventRating> eventRating = new HashSet<>();
}

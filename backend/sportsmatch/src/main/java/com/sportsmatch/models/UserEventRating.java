package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventRating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne private Rating userRating;
  @ManyToOne private Rating eventRating;
  @ManyToOne private User player;
  @ManyToOne private User opponent;
  @ManyToOne private Event event;
}

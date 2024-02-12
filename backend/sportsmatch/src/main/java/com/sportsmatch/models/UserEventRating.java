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

  @ManyToOne
  private UserRating userRating;
  @ManyToOne
  private EventRating eventRating;
  @ManyToOne
  private User playerRating;
  @ManyToOne
  private User playerRated;
  @ManyToOne
  private Event eventRated;
}

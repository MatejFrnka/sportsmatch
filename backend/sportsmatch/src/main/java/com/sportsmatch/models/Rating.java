package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Rating {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "text_rating")
  private String textRating;

  @Column(name = "star_rating")
  private Integer starRating;

  @Column(name = "created_at")
  @CreationTimestamp
  private LocalDateTime createdAt;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "userRating")
  private Set<UserEventRating> userRatings = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventRating")
  private Set<UserEventRating> eventRatings = new HashSet<>();
}

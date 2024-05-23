package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  @Id @GeneratedValue private Long id;

  @Column(name = "date_start")
  private LocalDateTime dateStart;

  @Column(name = "date_end")
  private LocalDateTime dateEnd;

  @Column(name = "min_elo")
  private Integer minElo;

  @Column(name = "max_elo")
  private Integer maxElo;

  private String title;

  @Column(name = "is_rank_updated")
  private Boolean isRanksUpdated = false;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  private Set<EventPlayer> players = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
  private Set<UserEventRating> ratings = new HashSet<>();

  @ManyToOne private Sport sport;

  @ManyToOne
  @JoinColumn(name = "place_id")
  private Place place;

  public Event(
      LocalDateTime dateStart,
      LocalDateTime dateEnd,
      String location,
      Integer minElo,
      Integer maxElo,
      String title,
      Sport sport,
      Place place) {
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.minElo = minElo;
    this.maxElo = maxElo;
    this.title = title;
    this.sport = sport;
    this.place = place;
  }
}

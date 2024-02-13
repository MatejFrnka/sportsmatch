package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "date_start")
  private LocalDateTime dateStart;

  @Column(name = "date_end")
  private LocalDateTime dateEnd;

  private String location;

  @Column(name = "min_elo")
  private Integer minElo;

  @Column(name = "max_elo")
  private Integer maxElo;

  private String title;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.EAGER)
  private Set<EventPlayer> players = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "event", fetch = FetchType.EAGER)
  private Set<UserEventRating> ratings = new HashSet<>();

  @ManyToOne private Sport sport;

  public Event(
      LocalDateTime dateStart,
      LocalDateTime dateEnd,
      String location,
      Integer minElo,
      Integer maxElo,
      String title,
      Sport sport) {
    this.dateStart = dateStart;
    this.dateEnd = dateEnd;
    this.location = location;
    this.minElo = minElo;
    this.maxElo = maxElo;
    this.title = title;
    this.sport = sport;
  }
}

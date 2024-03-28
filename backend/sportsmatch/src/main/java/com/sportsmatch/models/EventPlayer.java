package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_player")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPlayer {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name = "my_score")
  private Integer myScore;

  @Column(name = "opponent_score")
  private Integer opponentScore;

  @ManyToOne
  private User player;

  @ManyToOne
  private Event event;

  public EventPlayer(Integer myScore, Integer opponentScore, User player, Event event) {
    this.myScore = myScore;
    this.opponentScore = opponentScore;
    this.player = player;
    this.event = event;
  }
}

package com.sportsmatch.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "event_players")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "my_score")
    private Integer myScore;

    @Column(name = "opponent_score")
    private Integer opponentScore;

    @ManyToOne
    private User player;
}

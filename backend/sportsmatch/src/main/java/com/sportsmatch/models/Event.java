package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_start")
    private LocalDate dateStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_end")
    private LocalDate dateEnd;

    private String location;

    @Column(name = "min_elo")
    private Integer minElo;

    @Column(name = "max_elo")
    private Integer maxElo;

    private String title;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    private Set<EventPlayer> players = new HashSet<>();

    @ManyToOne
    private Sport sport;

}

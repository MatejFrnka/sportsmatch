package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "sports")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sport")
    private Set<SportUser> sportUsers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sport")
    private Set<Event> events = new HashSet<>();

    public Sport(String name) {
        this.name = name;
    }
}

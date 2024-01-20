package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private String username;

    private Gender gender;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<SportUser> sportUsers = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
    private Set<EventPlayer> eventsPlayed = new HashSet<>();

}

package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String email;

  private String password;

  private String name;

  private Gender gender;

  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "date_of_birth")
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private LocalDate dateOfBirth;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<SportUser> sportUsers = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "player")
  private Set<EventPlayer> eventsPlayed = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
  private Set<Token> tokens = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "player", fetch = FetchType.EAGER)
  private Set<UserEventRating> ratings = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "opponent", fetch = FetchType.EAGER)
  private Set<UserEventRating> rated = new HashSet<>();

  public User(String email, String password, String name, Gender gender, LocalDate dateOfBirth) {
    this.email = email;
    this.password = password;
    this.name = name;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.role = Role.USER;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  /**
   * Returns the user's email as the username.
   *
   * @return The email address.
   */
  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

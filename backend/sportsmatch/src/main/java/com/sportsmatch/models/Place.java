package com.sportsmatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "places")
public class Place {

  @Id
  @GeneratedValue
  public Long id;

  @NotBlank
  public String name;

  @NotBlank
  public String address;

  public double latitude;

  public double longitude;

  @OneToMany(mappedBy = "place")
  private Set<Event> events = new HashSet<>();


  public Place(String name, String address, Double latitude, Double longitude) {
    this.name = name;
    this.address = address;
    this.latitude = latitude;
    this.longitude = longitude;
  }
}

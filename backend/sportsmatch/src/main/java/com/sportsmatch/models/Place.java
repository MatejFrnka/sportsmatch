package com.sportsmatch.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

  @NotNull
  public Float latitude;

  @NotNull
  private Float longitude;
}

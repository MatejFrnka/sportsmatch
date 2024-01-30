package com.sportsmatch.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sports_users")
public class SportUser {
  @EmbeddedId private SportUserKey id;

  @ManyToOne
  @MapsId("userId")
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @MapsId("sportId")
  @JoinColumn(name = "sport_id")
  private Sport sport;

  public SportUser(User user, Sport sport) {
    this.user = user;
    this.sport = sport;
    this.id = new SportUserKey(user.getId(), sport.getId());
  }
}

package com.sportsmatch.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Embeddable
public class SportUserKey implements Serializable {

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "sport_id")
  private Long sportId;

  public SportUserKey(Long userId, Long sportId) {
    this.userId = userId;
    this.sportId = sportId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof SportUserKey that)) return false;
    return Objects.equals(userId, that.userId) && Objects.equals(sportId, that.sportId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, sportId);
  }
}

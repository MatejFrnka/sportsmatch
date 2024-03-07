package com.sportsmatch.dtos;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class SportDTO {
  public String name;

  public String emoji;

  public String backgroundUImageURL;

  public SportDTO(String name, String emoji, String backgroundUImageURL) {
    this.name = name;
    this.emoji = emoji;
    this.backgroundUImageURL = backgroundUImageURL;
  }
}

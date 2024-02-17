package com.sportsmatch.mappers;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.models.Sport;

public class SportMapper {

  /**
   * Converts a Sport entity to a SportDTO.
   *
   * @param entity The Sport entity to be converted.
   * @return SportDTO containing information from the Sport entity.
   */
  public static SportDTO toDTO(Sport entity) {
    return SportDTO.builder()
        .name(entity.getName())
        .emoji(entity.getEmoji())
        .backgroundUImageURL(entity.getBackgroundImageURL())
        .build();
  }
}

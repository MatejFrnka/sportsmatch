package com.sportsmatch.mappers;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.models.Sport;
import org.springframework.stereotype.Component;

@Component
public class SportMapper {

  /**
   * Converts a Sport entity to a SportDTO.
   *
   * @param entity The Sport entity to be converted.
   * @return SportDTO containing information from the Sport entity.
   */
  public SportDTO toDTO(Sport entity) {
    return SportDTO.builder()
        .name(entity.getName())
        .emoji(entity.getEmoji())
        .backgroundUImageURL(entity.getBackgroundImageURL())
        .build();
  }

  public Sport toEntity(SportDTO sportDTO) {
    return Sport.builder()
            .name(sportDTO.getName())
            .emoji(sportDTO.getEmoji())
            .backgroundImageURL(sportDTO.getBackgroundUImageURL())
            .build();
  }
}

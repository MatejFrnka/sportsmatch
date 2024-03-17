package com.sportsmatch.mappers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.models.Place;
import org.springframework.stereotype.Component;

@Component
public class PlaceMapper {

  /**
   * Converts a Place entity into a PlaceDTO object.
   *
   * @param entity The entity containing the data of the Place.
   * @return a PlaceDTO object with the given parameters from the Place entity.
   */
  public PlaceDTO toDTO(Place entity) {
    return PlaceDTO.builder()
        .name(entity.getName())
        .address(entity.getAddress())
        .latitude(entity.getLatitude())
        .longitude(entity.getLongitude())
        .build();
  }

  /**
   * Converts a PlaceDTO object into a Place entity.
   *
   * @param dto The PlaceDTO object containing the data of the Place.
   * @return a Place entity with the given parameters from the PlaceDTO object.
   */
  public Place toEntity(PlaceDTO dto) {
    return Place.builder()
        .name(dto.getName())
        .address(dto.getAddress())
        .latitude(dto.getLatitude())
        .longitude(dto.getLongitude())
        .build();
  }
}

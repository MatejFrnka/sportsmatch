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
  PlaceDTO toDTO(Place entity) {
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
   * @param DTO The PlaceDTO object containing the data of the Place.
   * @return a Place entity with the given parameters from the PlaceDTO object.
   */
  Place toEntity(PlaceDTO DTO) {
    return Place.builder()
        .name(DTO.getName())
        .address(DTO.getAddress())
        .latitude(DTO.getLatitude())
        .longitude(DTO.getLongitude())
        .build();
  }
}

package com.sportsmatch.services;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.mappers.PlaceMapper;
import com.sportsmatch.repositories.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlaceService {

  private final PlaceMapper placeMapper;
  private final PlaceRepository placeRepository;

  /**
   * Adds a new Place to the database based on PlaceDTO.
   *
   * @param placeDTO object containing the data about the new Place.
   * @return ResponseEntity with HTTP status(201) and message if it was successfully added.
   */
  public ResponseEntity<String> addNewPlace(PlaceDTO placeDTO) {
    placeRepository.save(placeMapper.toEntity(placeDTO));
    return ResponseEntity.status(HttpStatus.CREATED).body("Place successfully added");
  }

  /**
   * Searches for places based on name match and returns a list of corresponding PlaceDTO objects.
   *
   * @param name of the place to filter by.
   * @return a list of PlaceDTO objects that match the specified criteria.
   */
  public List<PlaceDTO> searchPlaces(String name) {
    return placeRepository.searchPlaces(name)
        .stream()
        .map(placeMapper::toDTO)
        .collect(Collectors.toList());
  }
}

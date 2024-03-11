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
  private final UserService userService;

  /**
   * Adds a new Place to the database based on PlaceDTO.
   *
   * @param placeDTO object containing the data about the new Place.
   * @return ResponseEntity with HTTP status and message to describe the problem.
   */
  public ResponseEntity<String> addNewPlace(PlaceDTO placeDTO) {
    if (userService.getUserFromContext() == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }
    placeRepository.save(placeMapper.toEntity(placeDTO));
    return ResponseEntity.status(HttpStatus.CREATED).body("Place successfully added");
  }

  /**
   * Return all Places from the database and map them to PlaceDTO object.
   *
   * @return a list of PlaceDTO object representing all places in the database.
   */
  public List<PlaceDTO> getAllPlaces() {
    return placeRepository.findAll()
        .stream()
        .map(placeMapper::toDTO)
        .collect(Collectors.toList());
  }

}

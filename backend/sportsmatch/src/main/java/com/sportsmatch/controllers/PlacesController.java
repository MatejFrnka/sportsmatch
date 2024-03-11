package com.sportsmatch.controllers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.services.PlaceService;
import com.sportsmatch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlacesController {

  private final PlaceService placeService;
  private final UserService userService;


  /**
   * Endpoint to add new Place based on provided PlaceDTO.
   *
   * @param placeDTO object containing the data about the new Place.
   * @return ResponseEntity with HTTP status and some meaningful message.
   */
  @PostMapping()
  public ResponseEntity<String> addNewPlace(@RequestBody PlaceDTO placeDTO) {
    return placeService.addNewPlace(placeDTO);
  }

  /**
   * Endpoint retrieve all places from the database and return them as PlaceDTO object.
   * <p>
   * If the user authenticated it returns a list of PlaceDTO.
   * If authentication fails, it throws a ResponseStatusException with status and message.
   *
   * @return list of PlaceDTO object representing all Places in the database.
   */
  @GetMapping()
  public List<PlaceDTO> getAllPlace() {
    try {
      userService.getUserFromContext();
      return placeService.getAllPlaces();
    } catch (ResponseStatusException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }
}

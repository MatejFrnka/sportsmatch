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
   * Endpoint allow user to search for places based on the provided name. If no name is provided, then returns all places.
   * <p>
   * The method first attempts to retrieve the user from the context to ensure authentication.
   * If the user is authenticated, it returns a list of places that match the provided name query, or all places if no name is provided.
   * If authentication fails, it throws a ResponseStatusException with status code UNAUTHORIZED and a message indicating that the user is not authenticated.
   *
   * @param name query String for searching places.
   * @return a list of PlaceDTO representing the places that match the provided name or all places if no name is provided.
   * @Throws ResponseStatusException if the user is not authenticated.
   */
  @GetMapping("/search")
  public List<PlaceDTO> searchPlaces(@RequestParam(required = false) String name) {
    try {
      userService.getUserFromContext();

      if (name == null || name.isEmpty()) {
        return placeService.getAllPlaces();
      } else {
        return placeService.searchPlacesByName(name);
      }
    } catch (ResponseStatusException e) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }
}

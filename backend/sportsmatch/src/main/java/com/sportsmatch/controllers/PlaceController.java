package com.sportsmatch.controllers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.services.PlaceService;
import com.sportsmatch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlaceController {

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
   *
   * @param name query String for searching places.
   * @return a list of PlaceDTO representing the places that match the provided name or all places if no name is provided.
   */
  @GetMapping("/search")
  public List<PlaceDTO> searchPlaces(@RequestParam(required = false) String name) {
    return placeService.searchPlaces(name);
  }
}

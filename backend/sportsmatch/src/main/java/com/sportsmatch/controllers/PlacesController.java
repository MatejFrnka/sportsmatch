package com.sportsmatch.controllers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.services.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/places")
public class PlacesController {

  private final PlaceService placeService;


  /**
   * Add new Place based on provided PlaceDTO.
   *
   * @param placeDTO object containing the data about the new Place.
   * @return ResponseEntity with HTTP status and some meaningful message.
   */
  @PostMapping()
  public ResponseEntity<String> addNewPlace(@RequestBody PlaceDTO placeDTO) {
    return placeService.addNewPlace(placeDTO);
  }

}

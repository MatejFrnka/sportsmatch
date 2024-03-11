package com.sportsmatch.controllers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.models.Place;
import com.sportsmatch.services.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

  @PostMapping("/add")
  public ResponseEntity<PlaceDTO> addNewPlace(@RequestBody PlaceDTO placeDTO) {
    return ResponseEntity.status(HttpStatus.CREATED).body(placeService.addNewPlace());
  }
}

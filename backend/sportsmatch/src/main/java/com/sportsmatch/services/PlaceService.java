package com.sportsmatch.services;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.mappers.PlaceMapper;
import com.sportsmatch.models.Place;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceService {

  private final PlaceMapper placeMapper;

  public PlaceDTO addNewPlace(){
    return null;
  }
}

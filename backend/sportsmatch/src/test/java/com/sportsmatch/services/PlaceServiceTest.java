package com.sportsmatch.services;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.mappers.PlaceMapper;
import com.sportsmatch.models.Place;
import com.sportsmatch.repositories.PlaceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PlaceServiceTest {
  @Mock
  private PlaceRepository placeRepository;
  @Mock
  private PlaceMapper placeMapper;
  @InjectMocks
  private PlaceService placeService;

  Place createPlaceEntity() {
    return Place.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude((789.012F))
        .build();
  }

  PlaceDTO createPlaceDTO() {
    return PlaceDTO.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude(789.012F)
        .build();
  }

  @Test
  public void testAddNewPlace() {
    when(placeMapper.toEntity(createPlaceDTO())).thenReturn(createPlaceEntity());

    ResponseEntity<String> response = placeService.addNewPlace(createPlaceDTO());

    verify(placeRepository, times(1)).save(createPlaceEntity());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Place successfully added", response.getBody());
  }

  @Test
  public void SearchPlacesWithExistName() {
    String name = "Test Place";

    List<Place> places = new ArrayList<>();
    places.add(createPlaceEntity());

    when(placeRepository.searchPlaces(name)).thenReturn(places);
    when(placeMapper.toDTO(createPlaceEntity())).thenReturn(createPlaceDTO());

    List<PlaceDTO> foundPlaces = placeService.searchPlaces(name);

    verify(placeRepository, times(1)).searchPlaces(name);
    assertEquals(1, foundPlaces.size());
    assertEquals("Test Place", foundPlaces.get(0).getName());
  }

  @Test
  public void SearchPlacesWithNonExistName() {
    String name = "NonExistent Place";

    List<Place> places = new ArrayList<>();

    when(placeRepository.searchPlaces(name)).thenReturn(places);

    List<PlaceDTO> foundPlaces = placeService.searchPlaces(name);

    verify(placeRepository, times(1)).searchPlaces(name);
    assertEquals(0, foundPlaces.size());
  }
}
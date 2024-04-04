package com.sportsmatch.services;

import com.sportsmatch.BaseTest;
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
public class PlaceServiceTest extends BaseTest {
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
        .latitude(123.456)
        .longitude(789.012)
        .build();
  }

  @Test
  public void addNewPlaceShouldReturnSuccessMessage() {
    Place placeEntity = createPlaceEntity();
    PlaceDTO placeDTO = createPlaceDTO();

    // Mocking PlaceMapper behavior to return a predefined Place entity
    when(placeMapper.toEntity(any())).thenReturn(placeEntity);

    // Invoking the method under test
    ResponseEntity<String> response = placeService.addNewPlace(placeDTO);

    // Verifying that placeRepository's save method is called once with the created placeEntity
    verify(placeRepository, times(1)).save(placeEntity);

    // Asserting the response status code and body
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Place successfully added", response.getBody());
  }

  @Test
  public void searchPlacesWithExistName() {
    String name = "Test Place";
    Place placeEntity = createPlaceEntity();
    PlaceDTO placeDTO = createPlaceDTO();

    // Mocking placeRepository behavior to return a list of places with the given name
    List<Place> places = new ArrayList<>();
    places.add(placeEntity);
    when(placeRepository.searchPlaces(name)).thenReturn(places);

    // Mocking PlaceMapper behavior to return a predefined PlaceDTO object
    when(placeMapper.toDTO(placeEntity)).thenReturn(placeDTO);

    // Invoking the method under test
    List<PlaceDTO> foundPlaces = placeService.searchPlaces(name);

    // Verifying that placeRepository's searchPlaces method is called once with the given name
    verify(placeRepository, times(1)).searchPlaces(name);

    // Asserting the size and attributes of the found places list
    assertEquals(1, foundPlaces.size());
    assertEquals("Test Place", foundPlaces.get(0).getName());
    assertEquals("Test Address", foundPlaces.get(0).getAddress());
    assertEquals(123.456D, foundPlaces.get(0).getLatitude());
    assertEquals(789.012D, foundPlaces.get(0).getLongitude());
  }

  @Test
  public void searchPlacesWithNonExistName() {
    String name = "NonExistent Place";
    Place placeEntity = createPlaceEntity();
    PlaceDTO placeDTO = createPlaceDTO();

    // Mocking placeRepository behavior to return an empty list of places
    List<Place> places = new ArrayList<>();
    when(placeRepository.searchPlaces(name)).thenReturn(places);

    // Invoking the method under test
    List<PlaceDTO> foundPlaces = placeService.searchPlaces(name);

    // Verifying that placeRepository's searchPlaces method is called once with the given name
    verify(placeRepository, times(1)).searchPlaces(name);
    assertEquals(0, foundPlaces.size());
  }
}
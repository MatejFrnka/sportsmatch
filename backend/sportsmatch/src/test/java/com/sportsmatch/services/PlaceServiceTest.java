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

  @Test
  public void testAddNewPlace() {
    // Given
    PlaceDTO placeDTO = PlaceDTO.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude(789.012F)
        .build();

    Place placeEntity = Place.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude((789.012F))
        .build();

    when(placeMapper.toEntity(placeDTO)).thenReturn(placeEntity);

    // When
    ResponseEntity<String> response = placeService.addNewPlace(placeDTO);

    // Then
    verify(placeRepository, times(1)).save(placeEntity);
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals("Place successfully added", response.getBody());
  }

  @Test
  public void testSearchPlaces() {
    // Given
    String name = "Test Place";

    Place placeEntity = Place.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude((789.012F))
        .build();

    List<Place> places = new ArrayList<>();
    places.add(placeEntity);

    PlaceDTO placeDTO = PlaceDTO.builder()
        .name("Test Place")
        .address("Test Address")
        .latitude(123.456F)
        .longitude(789.012F)
        .build();

    when(placeRepository.searchPlaces(name)).thenReturn(places);
    when(placeMapper.toDTO(placeEntity)).thenReturn(placeDTO);

    // When
    List<PlaceDTO> foundPlaces = placeService.searchPlaces(name);

    // Then
    verify(placeRepository, times(1)).searchPlaces(name);
    assertEquals(1, foundPlaces.size());
    assertEquals("Test Place", foundPlaces.get(0).getName());
  }
}
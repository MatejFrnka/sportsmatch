package com.sportsmatch.services;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.mappers.PlaceMapper;
import com.sportsmatch.models.Place;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.PlaceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class PlaceServiceTest {
  @Mock
  private PlaceRepository placeRepository;
  @Mock
  private UserService userService;
  @Mock
  private PlaceMapper placeMapper;
  @InjectMocks
  private PlaceService placeService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  // Creating PlaceDTO object
  private PlaceDTO createTestPlaceDTO() {
    return PlaceDTO.builder()
        .name("Place name")
        .address("Place address")
        .latitude(40.6892F)
        .longitude(74.0445F)
        .build();
  }

  // Creating Place entity
  private Place createTestPlace() {
    return Place.builder()
        .name("Place name")
        .address("Place address")
        .latitude(40.6892F)
        .longitude(74.0445F)
        .build();
  }

  @Test
  void addNewPlaceExceptAuthenticatedUser() {
    // Mocking userService.getUserFromContext and return a new user;
    when(userService.getUserFromContext()).thenReturn(new User());

    // Mocking the placeMapper.toEntity() method to convert PlaceDTO -> Place entity and return it with same data
    when(placeMapper.toEntity(any(PlaceDTO.class))).thenReturn(createTestPlace());

    // Calling hte placeService.addNewPlace() method with the test PlaceDTO object.
    // Except the response status code will be HttpStatus.CREATED,
    // Except the response body will be "Place successfully added"
    ResponseEntity<String> responseEntity = placeService.addNewPlace(createTestPlaceDTO());
    assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    assertEquals("Place successfully added", responseEntity.getBody());

    // Verifying the placeRepository.save() method was called only 1 time.
    verify(placeRepository, times(1)).save(any());
  }

  @Test
  void addNewPlaceExceptUnauthenticatedUser() {
    // Mocking userService.getUserFromContext and return a new user;
    when(userService.getUserFromContext()).thenReturn(null);

    // Mocking the placeMapper.toEntity() method to convert PlaceDTO -> Place entity and return it with same data
    when(placeMapper.toEntity(any(PlaceDTO.class))).thenReturn(createTestPlace());

    // Calling hte placeService.addNewPlace() method with the test PlaceDTO object.
    // Except the response status code will be HttpStatus.UNAUTHORIZED,
    // Except the response body will be "User not authenticated"
    ResponseEntity<String> responseEntity = placeService.addNewPlace(createTestPlaceDTO());
    assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    assertEquals("User not authenticated", responseEntity.getBody());

    // Verifying the placeRepository.save() method was called only 1 time.
    verify(placeRepository, times(0)).save(any());
  }

  @Test
  void searchPlacesByNameShouldReturnCorrectDTOs() {
    // Name to search for
    String name = "Place name";
    List<Place> places = Arrays.asList(
        Place.builder().id(1L).name("Place name").build(),
        Place.builder().id(2L).name("Place name").build());

    // Map the list of Place entity to a list of PlaceDTO objects
    List<PlaceDTO> expectedDTOs = places.stream()
        .map(placeMapper::toDTO)
        .toList();

    // Mock the behavior of PlaceRepository.findByName(name)to return the given list of Places
    when(placeRepository.findByName(name)).thenReturn(places);

    // Call the searchPlacesByName method with the provided name
    List<PlaceDTO> resultDTOs = placeService.searchPlacesByName(name);

    // Assert the size of the excepted and result lists are equal
    // Iterate over pair of excepted and result DTOs to ensure they match
    assertEquals(expectedDTOs.size(), resultDTOs.size());
    for (int i = 0; i < expectedDTOs.size(); i++) {
      assertEquals(expectedDTOs.get(i), resultDTOs.get(i));
    }
  }
  @Test
  void searchPlacesByNameShouldReturnEmptyListWhenNoPlacesFound() {
    // Name to search for
    String name = "Non-exist place name";

    // Mock the behavior of PlaceRepository.findByName(name)to return empty list of Places
    when(placeRepository.findByName(name)).thenReturn(Collections.emptyList());

    // Call the searchPlacesByName method with the provided name
    List<PlaceDTO> resultDTOs = placeService.searchPlacesByName(name);

    // Assert the list is empty with the given name
    assertTrue(resultDTOs.isEmpty());
  }
}
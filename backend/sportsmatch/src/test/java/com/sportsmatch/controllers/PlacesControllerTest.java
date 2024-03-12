package com.sportsmatch.controllers;

import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.models.User;
import com.sportsmatch.services.PlaceService;
import com.sportsmatch.services.UserService;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ApplicationFilterConfig.class})
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
@Import(com.sportsmatch.SportsmatchApplication.class)
class PlacesControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PlaceService placeService;

  @Autowired
  private UserService userService;

  @Test
  void addNewPlace() {
  }

  @Test
  void searchPlacesWithoutName() throws Exception {

    // Set up UserService to return a new user.
    when(userService.getUserFromContext()).thenReturn(new User());

    // Set up the PlaceService to return and empty list when the getAllPlaces was called.
    when(placeService.getAllPlaces()).thenReturn(Collections.emptyList());

    // Perform HTTP GET request to the "/search" endpoint.
    // Response status code is OK (200) and response content matches the empty list.
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places/search"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  void searchPlacesWitName() throws Exception {

    // Set up UserService to return a new user.
    when(userService.getUserFromContext()).thenReturn(new User());

    // Set up the PlaceService to return list of PlaceDTOs when the searchPlacesByName was called.
    List<PlaceDTO> places = Arrays.asList(

        PlaceDTO.builder()
            .name("Place name")
            .address("Place address")
            .latitude(40.6892F)
            .longitude(74.0445F)
            .build(),

        PlaceDTO.builder()
            .name("Place name")
            .address("Place address 2")
            .latitude(40.6893F)
            .longitude(74.0446F)
            .build());

    when(placeService.searchPlacesByName("Place name")).thenReturn(places);

    // Perform HTTP GET request to the "/search" endpoint.
    // Response status code is OK (200) and response content matches the list of PlacesDTO.
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places/search")
            .param("name", "Place name"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("["
            + "{\"name\":\"Place name\",\"address\":\"Place address\",\"latitude\":40.6892,\"longitude\":74.0445},"
            + "{\"name\":\"Place name\",\"address\":\"Place address 2\",\"latitude\":40.6893,\"longitude\":74.0446}"
            + "]"));
  }

  @Test
  public void searchPlacesUnauthenticatedUserShouldReturnsUnauthorized() throws Exception {

    // Set up UserService to throw ResponseStatusException (UNAUTHORIZED).
    when(userService.getUserFromContext()).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));

    // Perform HTTP GET request to the "/search" endpoint.
    // Response status code is UNAUTHORIZED (401).
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places/search")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

}
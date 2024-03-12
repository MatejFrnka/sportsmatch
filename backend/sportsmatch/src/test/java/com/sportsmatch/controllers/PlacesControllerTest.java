package com.sportsmatch.controllers;

import com.sportsmatch.models.User;
import com.sportsmatch.services.PlaceService;
import com.sportsmatch.services.UserService;
import org.apache.catalina.core.ApplicationFilterConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
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

import java.util.Collections;

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

    when(userService.getUserFromContext()).thenReturn(new User());
    when(placeService.getAllPlaces()).thenReturn(Collections.emptyList());

    mockMvc.perform(MockMvcRequestBuilders.get("/search"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  void searchPlacesWitName() throws Exception {
    when(userService.getUserFromContext()).thenReturn(new User());
    when(placeService.searchPlacesByName("Test")).thenReturn(Collections.emptyList());

    mockMvc.perform(MockMvcRequestBuilders.get("/search")
            .param("name", "Test"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().json("[]"));
  }

  @Test
  public void searchPlacesUnauthenticatedUserShouldReturnsUnauthorized() throws Exception {
    when(userService.getUserFromContext()).thenThrow(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated"));

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/places/search")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

}
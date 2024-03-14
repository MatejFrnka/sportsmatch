package com.sportsmatch.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.services.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class PlaceControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private PlaceService placeService;

  @Test
  void addNewPlaceShouldReturn403NotAuthenticatedUser() throws Exception {
    PlaceDTO placeDTO = PlaceDTO.builder()
        .name("Test Place Name")
        .address("Test Address")
        .latitude(45.32F)
        .longitude(45.32F)
        .build();

    when(placeService.addNewPlace(any(PlaceDTO.class)))
        .thenReturn(ResponseEntity.ok("Place added successfully"));

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/places/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(placeDTO)))
        .andExpect(MockMvcResultMatchers.status().isForbidden());
  }

  @Test
  @WithMockUser(username = "testuser")
  void addNewPlaceShouldReturn200AndSuccessfulMessageAuthenticatedUser() throws Exception {
    PlaceDTO placeDTO = PlaceDTO.builder()
        .name("Test Place Name")
        .address("Test Address")
        .latitude(45.32F)
        .longitude(45.32F)
        .build();

    when(placeService.addNewPlace(any(PlaceDTO.class)))
        .thenReturn(ResponseEntity.ok("Place successfully added"));

    mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/places/add")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(placeDTO)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("Place successfully added"));
  }

  @Test
  void searchPlaces() {
  }
}
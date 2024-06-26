package com.sportsmatch.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsmatch.BaseTest;
import com.sportsmatch.auth.JwtService;
import com.sportsmatch.dtos.PlaceDTO;
import com.sportsmatch.models.Gender;
import com.sportsmatch.models.Role;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.UserRepository;
import com.sportsmatch.services.PlaceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class PlaceControllerTest extends BaseTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @Autowired private JwtService jwtService;

  @Autowired private UserRepository userRepository;

  @MockBean private PlaceService placeService;

  PlaceDTO createPlaceDTO1() {
    return PlaceDTO.builder()
        .name("Test Place Name1")
        .address("Test Address1")
        .latitude(123.456)
        .longitude(789.012)
        .build();
  }

  PlaceDTO createPlaceDTO2() {
    return PlaceDTO.builder()
        .name("Test Place Name2")
        .address("Test Address2")
        .latitude(123.456)
        .longitude(789.012)
        .build();
  }

  @Test
  void addNewPlaceShouldReturn403NotAuthenticatedUser() throws Exception {
    // Create a PlaceDTO object for testing
    PlaceDTO placeDTO = createPlaceDTO1();

    // Mock the behavior of placeService.addNewPlace() to return success response
    when(placeService.addNewPlace(any(PlaceDTO.class)))
        .thenReturn(ResponseEntity.ok("Place added successfully"));

    // Perform a POST request to add a new place without authentication
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/places/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDTO)))
        // Verify that the response status is 401 Forbidden
        .andExpect(MockMvcResultMatchers.status().isUnauthorized());
  }

  @Test
  @WithMockUser(username = "testuser")
  void addNewPlaceShouldReturn200AndSuccessfulMessageAuthenticatedUser() throws Exception {
    // Create a PlaceDTO object for testing
    PlaceDTO placeDTO = createPlaceDTO1();

    // Mock the behavior of placeService.addNewPlace() to return success response
    when(placeService.addNewPlace(any(PlaceDTO.class)))
        .thenReturn(ResponseEntity.ok("Place successfully added"));

    // Create User
    User user =
        User.builder()
            .email("testuser@mail.com")
            .password("password")
            .name("testuser")
            .gender(Gender.MALE)
            .role(Role.USER)
            .build();
    userRepository.save(user);
    String token = jwtService.generateToken(user);

    // Perform a POST request to add a new place with authentication
    mockMvc
        .perform(
            MockMvcRequestBuilders.post("/api/v1/places/add")
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(placeDTO)))
        // Verify that the response status is 200 OK
        .andExpect(MockMvcResultMatchers.status().isOk())
        // Verify that the response content contains the expected success message
        .andExpect(MockMvcResultMatchers.content().string("Place successfully added"));
  }

  @Test
  void searchPlaces() throws Exception {

    PlaceDTO placeDTO1 = createPlaceDTO1();
    PlaceDTO placeDTO2 = createPlaceDTO2();
    List<PlaceDTO> expectedPlaces = Arrays.asList(placeDTO1, placeDTO2);

    // Mock the behavior of the placeService to return the expected list of places
    when(placeService.searchPlaces(any(String.class))).thenReturn(expectedPlaces);

    // Perform a GET request to search for places with a name parameter "test"
    mockMvc
        .perform(
            MockMvcRequestBuilders.get("/api/v1/places/search")
                .param("name", "test")
                .contentType(MediaType.APPLICATION_JSON))

        // Verify the response is an array
        .andExpect(MockMvcResultMatchers.jsonPath("$").isArray())
        // Verify the name of the first place in the response matches the name of the first expected
        // place
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Test Place Name1"))
        // Verify the name of the second place in the response matches the name of the second
        // expected place
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Test Place Name2"));
  }
}

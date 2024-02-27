package com.sportsmatch.services;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.models.Gender;
import com.sportsmatch.models.Sport;
import com.sportsmatch.models.SportUser;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest {

  @Mock private UserRepository userRepository;
  @Mock private SportMapper sportMapper;
  @Mock private SecurityContext securityContext;
  @InjectMocks private UserServiceImp userServiceImp;

  @Test
  void updateUserInfo() {
    List<SportDTO> sports = new ArrayList<>();
    SportUser sportUser = mock(SportUser.class);
    Set<SportUser> sportUsers = new HashSet<>();
    UserInfoDTO userInfoDTO =
        UserInfoDTO.builder()
            .userName("John Doe")
            .dateOfBirth("01-01-2024")
            .gender("male")
            .sports(sports)
            .build();

    // getUserFromTheSecurityContextHolder behaviour
    Authentication authentication = mock(Authentication.class);
    UserDetails userDetails = mock(UserDetails.class);
    User user = mock(User.class);
    SecurityContextHolder.setContext(securityContext);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    when(authentication.isAuthenticated()).thenReturn(true);
    when(authentication.getPrincipal()).thenReturn(userDetails);
    when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

    // name
    doNothing().when(user).setName(userInfoDTO.getUserName());

    // parse date of birth
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate localDate = LocalDate.parse(userInfoDTO.getDateOfBirth(), formatter);
    doNothing().when(user).setDateOfBirth(localDate);

    // gender
    doNothing().when(user).setGender(Gender.valueOf(userInfoDTO.getGender().toUpperCase()));

    // mapper
    SportDTO sportDTO = mock(SportDTO.class);
    Sport sport = mock(Sport.class);
    when(sportMapper.toEntity(sportDTO)).thenReturn(sport);

    // link user with sport
    sportUsers.add(sportUser);
    when(user.getSportUsers()).thenReturn(sportUsers);
    user.getSportUsers().add(sportUser);
    when(userRepository.save(any())).thenReturn(user);

    userServiceImp.updateUserInfo(userInfoDTO);

    verify(user).setName("John Doe");
    verify(user)
        .setDateOfBirth(LocalDate.parse("01-01-2024", DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    verify(user).setGender(Gender.MALE);
    Sport returnedSport = sportMapper.toEntity(sportDTO);
    assertEquals(sport, returnedSport);
    verify(user, times(1)).getSportUsers();
    assertTrue(sportUsers.contains(sportUser));
    verify(userRepository).save(user);
  }
}

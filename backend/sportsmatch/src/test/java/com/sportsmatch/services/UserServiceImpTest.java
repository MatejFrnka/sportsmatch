package com.sportsmatch.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.sportsmatch.BaseTest;
import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.UserRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ExtendWith(MockitoExtension.class)
class UserServiceImpTest extends BaseTest {

  @Mock private UserRepository userRepository;
  @Mock private SportMapper sportMapper;
  @Mock private SecurityContext securityContext;
  @Mock private RankService rankService;
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

  @Test
  void getUserById() {
    String username = "testUser";

    // User
    User user = new User();
    user.setId(1L);
    user.setName(username);
    user.setRank(1000);
    when(userRepository.findUserById(user.getId())).thenReturn(Optional.of(user));

    // Sport
    Sport sport = new Sport();
    sport.setId(1L);
    sport.setName("Tennis");
    sport.setEmoji("🎾");
    sport.setBackgroundImageURL("./assets/sport-component-tennis.png");

    // SportUser
    SportUser sportUser = new SportUser(user, sport);
    user.getSportUsers().add(sportUser);

    // SportDTO
    SportDTO sportDTO =
        SportDTO.builder()
            .name(sport.getName())
            .emoji(sport.getEmoji())
            .backgroundUImageURL(sport.getBackgroundImageURL())
            .build();

    List<SportDTO> sports = new ArrayList<>();
    sports.add(sportDTO);

    // Event
    Event event = mock(Event.class);
    event.setSport(sport);

    EventPlayer eventPlayer = mock(EventPlayer.class);

    when(eventPlayer.getEvent()).thenReturn(event);
    when(eventPlayer.getEvent().getSport()).thenReturn(sport);

    user.getEventsPlayed().add(eventPlayer);

    doNothing().when(rankService).updatePlayersRanks(event);

    UserDTO expectedUserDTO =
        UserDTO.builder().name(user.getName()).elo(user.getRank()).sports(sports).build();

    UserDTO actualUserDTO = userServiceImp.getUserById(user.getId());

    assertEquals(expectedUserDTO.getName(), actualUserDTO.getName());
    assertEquals(expectedUserDTO.getElo(), actualUserDTO.getElo());
    assertEquals(expectedUserDTO.getSports().size(), actualUserDTO.getSports().size());
  }
}

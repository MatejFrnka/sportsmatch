package com.sportsmatch.services;

import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final SportMapper sportMapper;
  private final SportRepository sportRepository;
  private final UserMapper userMapper;
  private final RankService rankService;

  /**
   * This method retrieves the authenticated user from the SecurityContextHolder. It checks if the
   * user is authenticated and returns the corresponding User entity.
   *
   * @return the authenticated user
   * @throws ResponseStatusException if the user is not authenticated
   */
  @Override
  public User getUserFromContext() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null
        && authentication.isAuthenticated()
        && authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }

  public UserDTO getUserDTOFromContext() {
    return userMapper.toDTO(getUserFromContext());
  }

  public UserDTO getMyRank() {
    User user = getUserFromContext();
    return getUserById(user.getId());
  }

  public UserDTO getUserById(Long id) {
    Optional<User> user = userRepository.findUserById(id);

    if (user.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    Set<SportUser> sportUsers = user.get().getSportUsers();
    List<Sport> sports = sportUsers.stream().map(SportUser::getSport).toList();

    List<EventPlayer> events = new ArrayList<>(user.get().getEventsPlayed());
//    HashSet<SportDTO> sports = new HashSet<>();
//    for (EventPlayer e : events) {
//      rankService.updatePlayersRanks(e.getEvent());
//      sports.add(sportMapper.toDTO(e.getEvent().getSport()));
//    }

    return UserDTO.builder()
        .name(user.get().getName())
        .sports(sports.stream().toList())
        .elo(user.get().getRank())
        .win(user.get().getWin())
        .loss(user.get().getLoss())
        .build();
  }

  @Transactional
  public void updateUserInfo(UserInfoDTO userInfoDTO) {
    User user = getUserFromContext();
    user.setName(userInfoDTO.getUserName());
    parseUserDateOfBirth(userInfoDTO, user);
    user.setGender(Gender.valueOf(userInfoDTO.getGender().toUpperCase()));
    linkUserWithSport(userInfoDTO, user);
    userRepository.save(user);
  }

  private void parseUserDateOfBirth(UserInfoDTO userInfoDTO, User user) {
    try {
      user.setDateOfBirth(
          LocalDate.parse(userInfoDTO.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    } catch (DateTimeParseException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
  }

  private void linkUserWithSport(UserInfoDTO userInfoDTO, User user) {
    for (SportDTO sportDTO : userInfoDTO.getSports()) {
      Optional<Sport> optionalSport = sportRepository.findSportByName(sportDTO.getName());

      if (optionalSport.isPresent()) {
        Sport sport = optionalSport.get();

        boolean isAssociated =
            user.getSportUsers().stream()
                .anyMatch(sportUser -> Objects.equals(sportUser.getSport().getId(), sport.getId()));

        if (!isAssociated) {
          SportUser sportUser = new SportUser(user, sport);
          user.getSportUsers().add(sportUser);
        }
      } else {
        throw new ResponseStatusException(
            HttpStatus.NOT_FOUND, "Sport not found: " + sportDTO.getName());
      }
    }
  }
}

package com.sportsmatch.services;

import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.Sport;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.models.Gender;
import com.sportsmatch.models.SportUser;
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
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final SportMapper sportMapper;
  private final SportRepository sportRepository;
  private final UserMapper userMapper;

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

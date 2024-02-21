package com.sportsmatch.services;

import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.mappers.SportMapper;
import com.sportsmatch.models.Gender;
import com.sportsmatch.models.SportUser;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
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

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;
  private final SportMapper sportMapper;
  private final SportRepository sportRepository;

  /**
   * Retrieves the currently authenticated user from the security context.
   *
   * @return The authenticated user
   */
  @Override
  public User getUserFromTheSecurityContextHolder() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null
        && authentication.isAuthenticated()
        && authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }

  @Transactional
  public void updateUserInfo(UserInfoDTO userInfoDTO) {
    User user = getUserFromTheSecurityContextHolder();
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
      if (sportRepository.findSportByName(sportDTO.getName()).isEmpty()) {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }
      SportUser sportUser = new SportUser(user, sportMapper.toEntity(sportDTO));
      user.getSportUsers().add(sportUser);
    }
  }
}

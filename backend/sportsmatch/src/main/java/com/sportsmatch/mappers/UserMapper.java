package com.sportsmatch.mappers;

import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.SportDTO;
import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.models.Role;
import com.sportsmatch.models.SportUser;
import com.sportsmatch.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;
  private final SportMapper sportMapper;

  public User registerToUser(AuthRequestDTO authRequestDTO) {
    return User.builder()
        .email(authRequestDTO.getEmail())
        .password(passwordEncoder.encode(authRequestDTO.getPassword()))
        .role(Role.USER)
        .build();
  }

  public UserDTO toDTO(User user) {
    List<SportDTO> userSports = user.getSportUsers().stream()
            .map(SportUser::getSport)
            .map(sportMapper::toDTO)
            .toList();
    return UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .sports(userSports)
            .elo(user.getRank())
            .win(user.getWin())
            .loss(user.getLoss())
            .build();
  }
}

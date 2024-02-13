package com.sportsmatch.mappers;

import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.models.Role;
import com.sportsmatch.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User registerToUser(AuthRequestDTO authRequestDTO) {
    return User.builder()
        .email(authRequestDTO.getEmail())
        .password(passwordEncoder.encode(authRequestDTO.getPassword()))
        .role(Role.USER)
        .build();
  }

  public UserDTO toDTO (User user){
    return UserDTO.builder()
        .name(user.getName())
        .build();
  }
}

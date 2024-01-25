package com.sportsmatch.mappers;

import com.sportsmatch.dtos.RequestDTO;
import com.sportsmatch.models.Role;
import com.sportsmatch.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class UserMapper {

  private final PasswordEncoder passwordEncoder;

  public User registerToUser(RequestDTO requestDTO) {

    if (isNullEmailOrPassword(requestDTO) || isInvalidEmail(requestDTO.getEmail())) {
      return null;
    }

    return User.builder()
        .email(requestDTO.getEmail())
        .password(passwordEncoder.encode(requestDTO.getPassword()))
        .role(Role.USER)
        .build();
  }

  // todo delete this check
  private boolean isNullEmailOrPassword(RequestDTO requestDTO) {
    return requestDTO.getEmail() == null
        || requestDTO.getPassword() == null
        || requestDTO.getPassword().trim().isEmpty();
  }

  // todo use @Email annotation
  private boolean isInvalidEmail(String email) {
    String regexPattern =
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    return !Pattern.compile(regexPattern).matcher(email).matches();
  }
}

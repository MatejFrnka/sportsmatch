package com.sportsmatch.auth;

import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.AuthResponseDTO;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.User;
import com.sportsmatch.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserMapper userMapper;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

/**
 * Registers a new user based on the provided authentication request DTO.
 *
 * @param authRequestDTO The authentication request DTO containing user details.
*/
  public void register(AuthRequestDTO authRequestDTO) {
    User user = userMapper.registerToUser(authRequestDTO);
    userRepository.save(user);
  }

/**
 * Authenticates a user based on the provided authentication request DTO.
 *
 * @param authRequestDTO The authentication request DTO containing user credentials.
 * @return AuthResponseDTO with a generated JWT token upon successful authentication.
*/
  public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(authRequestDTO.getEmail(), authRequestDTO.getPassword()));
    User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return AuthResponseDTO.builder()
        .token(jwtToken)
        .build();
  }
}

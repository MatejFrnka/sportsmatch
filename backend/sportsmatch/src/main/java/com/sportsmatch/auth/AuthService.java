package com.sportsmatch.auth;

import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.AuthResponseDTO;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

  private final UserMapper userMapper;
  private final JwtService jwtService;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  public void register(AuthRequestDTO authRequestDTO) {
    User user = userMapper.registerToUser(authRequestDTO);
    userRepository.save(user);
  }

  public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            authRequestDTO.getEmail(), authRequestDTO.getPassword()));
    User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return AuthResponseDTO.builder().token(jwtToken).build();
  }
}

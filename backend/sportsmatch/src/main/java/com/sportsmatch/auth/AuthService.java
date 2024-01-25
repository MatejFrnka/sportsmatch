package com.sportsmatch.auth;

import com.sportsmatch.dtos.RequestDTO;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.User;
import com.sportsmatch.repos.UserRepository;
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

  public RequestDTO register(RequestDTO requestDTO) {
    User user = userMapper.registerToUser(requestDTO);
    if (user == null) {
      return RequestDTO.builder().status("Invalid User Data").build();
    }
    user.setUsername("myUserName");
    userRepository.save(user);
    return RequestDTO.builder()
        .email(requestDTO.getEmail())
        .status("Registered Successfully")
        .build();
  }

  public RequestDTO authenticate(RequestDTO requestDTO) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(requestDTO.getEmail(), requestDTO.getPassword()));
    User user = userRepository.findByEmail(requestDTO.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    return RequestDTO.builder()
        .email(requestDTO.getEmail())
        .status("Logged in successfully")
        .token(jwtToken)
        .build();
  }
}

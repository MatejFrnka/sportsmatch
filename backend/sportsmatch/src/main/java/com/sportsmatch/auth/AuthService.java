package com.sportsmatch.auth;

import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.AuthResponseDTO;
import com.sportsmatch.mappers.UserMapper;
import com.sportsmatch.models.Token;
import com.sportsmatch.models.TokenType;
import com.sportsmatch.models.User;
import com.sportsmatch.repos.TokenRepository;
import com.sportsmatch.repos.UserRepository;
import java.util.List;
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
  private final TokenRepository tokenRepository;

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
        new UsernamePasswordAuthenticationToken(
            authRequestDTO.getEmail(), authRequestDTO.getPassword()));
    User user = userRepository.findByEmail(authRequestDTO.getEmail()).orElseThrow();
    String jwtToken = jwtService.generateToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
    return AuthResponseDTO.builder().token(jwtToken).build();
  }

  /**
   * Revokes all valid tokens associated with the provided user.
   *
   * @param user The user for whom to revoke tokens.
   */
  private void revokeAllUserTokens(User user) {
    List<Token> validUserToken = tokenRepository.findAllValidTokensByUser(user.getId());
    if (!validUserToken.isEmpty()) {
      for (Token t : validUserToken) {
        t.setRevoked(true);
        t.setExpired(true);
      }
    }
    tokenRepository.saveAll(validUserToken);
  }

  /**
   * Saves the user's JWT token to the token repository.
   *
   * @param user The user associated with the token.
   * @param jwtToken The JWT token to be saved.
   */
  private void saveUserToken(User user, String jwtToken) {
    tokenRepository.save(
        Token.builder()
            .user(user)
            .token(jwtToken)
            .tokenType(TokenType.BEARER)
            .isExpired(false)
            .isRevoked(false)
            .build());
  }
}

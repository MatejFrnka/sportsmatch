package com.sportsmatch.auth;

import com.sportsmatch.models.Token;
import com.sportsmatch.models.TokenType;
import com.sportsmatch.models.User;
import com.sportsmatch.repos.TokenRepository;
import com.sportsmatch.repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final JwtAuthFilter authFilter;
  private final TokenRepository tokenRepository;
  private final UserRepository userRepository;
  private final JwtService jwtService;

  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

    final String authHeader = request.getHeader("Authorization");
    final String jwt = authHeader.substring(7);
    final String userEmail = jwtService.extractUserName(jwt);
    Optional<User> user = userRepository.findByEmail(userEmail);

    if (authFilter.isBearerTokenNotPresent(authHeader) || user.isEmpty()) {
      return;
    }

    tokenRepository.save(
        Token.builder()
            .token(jwt)
            .user(user.get())
            .tokenType(TokenType.BEARER)
            .isValid(false)
            .build());
  }
}

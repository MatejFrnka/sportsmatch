package com.sportsmatch.auth;

import com.sportsmatch.models.Token;
import com.sportsmatch.repos.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final JwtAuthFilter authFilter;
  private final TokenRepository tokenRepository;

  /**
   * Handles the logout process by invalidating the JWT token associated with the request.
   *
   * @param request The HttpServletRequest.
   * @param response The HttpServletResponse.
   * @param authentication The Authentication object representing the authenticated user.
   */
  @Override
  public void logout(
      HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    final String authHeader = request.getHeader("Authorization");
    if (authFilter.isBearerTokenNotPresent(authHeader)) {
      return;
    }
    final String jwt = authHeader.substring(7);
    Token storedToken = tokenRepository.findByToken(jwt).orElse(null);
    if (storedToken != null) {
      storedToken.setExpired(true);
      storedToken.setRevoked(true);
      tokenRepository.save(storedToken);
    }
  }
}

package com.sportsmatch.auth;

import com.sportsmatch.repositories.TokenRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;
  private final TokenRepository tokenRepository;

  @Override
  protected void doFilterInternal(
          @NonNull HttpServletRequest request,
          @NonNull HttpServletResponse response,
          @NonNull FilterChain filterChain)
          throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization");

    if (isBearerTokenNotPresent(authHeader)) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = authHeader.substring(7);
      final String userEmail = jwtService.extractUserName(jwt);
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      if (isAuthenticationNeeded(userEmail) && jwtService.isTokenValid(jwt, userDetails)) {
        if (!tokenRepository.existsByToken(jwt)) {
          updateSecurityContext(request, userDetails);
        }
      } else {
        sendUnauthorizedError(response, "Invalid or expired token");
        return;
      }
    } catch (JwtException e) {
      sendUnauthorizedError(response, "Invalid token");
      return;
    }

    filterChain.doFilter(request, response);
  }

  public boolean isBearerTokenNotPresent(String authHeader) {
    if (authHeader == null) return true;
    String[] tokenParts = authHeader.split(" ");
    return !authHeader.startsWith("Bearer ") || tokenParts.length != 2;
  }

  private boolean isAuthenticationNeeded(String userEmail) {
    return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private void updateSecurityContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

  private void sendUnauthorizedError(HttpServletResponse response, String errorMessage) throws IOException {
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, errorMessage);
  }
}

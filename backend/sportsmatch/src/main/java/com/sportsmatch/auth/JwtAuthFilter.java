package com.sportsmatch.auth;

import com.sportsmatch.repositories.TokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

    final String jwt;
    final String userEmail;

    try {
      jwt = authHeader.substring(7);
      userEmail = jwtService.extractUserName(jwt);
    } catch (Exception e) {
      filterChain.doFilter(request, response);
      return;
    }

    if (isUserAuthenticated(userEmail)) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails) && !tokenRepository.existsByToken(jwt)) {
        updateSecurityContext(request, userDetails);
      }
    }
    filterChain.doFilter(request, response);
  }

  public boolean isBearerTokenNotPresent(String authHeader) {
    if (authHeader == null) {
      return true;
    }
    String[] tokenParts = authHeader.split(" ");
    return !authHeader.startsWith("Bearer ") || tokenParts.length != 2;
  }

  public boolean isUserAuthenticated(String userEmail) {
    return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
  }

  public void updateSecurityContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }
}

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

    final String jwt;
    final String userEmail;

    try {
      jwt = authHeader.substring(7);
      userEmail = jwtService.extractUserName(jwt);
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

      // Check user authentication
      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        // Validate the token
        if (jwtService.isTokenValid(jwt, userDetails)) {
          if (!tokenRepository.existsByToken(jwt)) {
            // Token is valid, update security context
            updateSecurityContext(request, userDetails);
          }
        } else {
          // Token is invalid or expired
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
          return;
        }
      }
    } catch (JwtException e) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
      return;
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

  public void updateSecurityContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }
}

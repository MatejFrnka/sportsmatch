package com.sportsmatch.auth;

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

/**
*
 * @param request The HttpServletRequest object.
 * @param response The HttpServletRequest object.
 * @param filterChain The FilterChain for processing the request and response.
 * @throws ServletException If a servlet-related exception occurs.
 * @throws IOException If an I/O-related exception occurs.
*/
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

    final String jwt = authHeader.substring(7);
    final String userEmail = jwtService.extractUserName(jwt);

    if (isUserAuthenticated(userEmail)) {
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
      if (jwtService.isTokenValid(jwt, userDetails)) {
        updateSecurityContext(request, userDetails);
      }
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Checks if a valid Bearer token is present in the provided authorization header.
   *
   * @param authHeader holds the JWT Token
   * @return True if a valid token is present; false otherwise.
   */
  public boolean isBearerTokenNotPresent(String authHeader) {
    if (authHeader == null) {
      return true;
    }
    String[] tokenParts = authHeader.split(" ");
    return !authHeader.startsWith("Bearer ") || tokenParts.length != 2;
  }

  /**
   * Checks if a user with the specified email is authenticated.
   *
   * @param userEmail The email of the user to check.
   * @return true if the user is authenticated; false otherwise.
   */
  public boolean isUserAuthenticated(String userEmail) {
    return userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null;
  }

/**
* Updates the security context with the authenticated user details.
 * @param request The HttpServletRequest associated with the authentication request.
 * @param userDetails The UserDetails representing the authenticated user.
*/
  public void updateSecurityContext(HttpServletRequest request, UserDetails userDetails) {
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authToken);
  }

}

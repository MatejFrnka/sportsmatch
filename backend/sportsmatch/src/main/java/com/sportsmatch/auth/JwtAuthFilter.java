package com.sportsmatch.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    final String authHeader = request.getHeader("Authorization"); // header that contains JWT Token
    final String jwt;
    final String userEmail;

    if (authHeader == null
        || !authHeader.startsWith("Bearer ")) { // check header if contains JWT Token
      filterChain.doFilter(request, response);
      return;
    }

    jwt = authHeader.substring(7); // takes JWT Token from header, index from "Bearer "
    userEmail = jwtService.extractUserName(jwt); // takes userEmail from JWT Token

    if (userEmail != null
        && SecurityContextHolder.getContext().getAuthentication() == null) { // check if user is authenticated
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
    }
  }
}

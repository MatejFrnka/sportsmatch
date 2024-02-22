package com.sportsmatch.services;

import com.sportsmatch.models.User;
import com.sportsmatch.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

  private final UserRepository userRepository;

  /**
   * Retrieves the currently authenticated user from the security context.
   *
   * @return The authenticated user
   */
  @Override
  public User getUserFromTheSecurityContextHolder() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null
        && authentication.isAuthenticated()
        && authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }
}

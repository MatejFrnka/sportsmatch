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
   * This method retrieves the authenticated user from the SecurityContextHolder.
   * It checks if the user is authenticated and returns the corresponding User entity.
   *
   * @return the authenticated user
   * @throws ResponseStatusException if the user is not authenticated
   */
  @Override
  public User getUserFromTheSecurityContextHolder() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userRepository.findByEmail(userDetails.getUsername()).orElseThrow();
    } else {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authenticated");
    }
  }

}

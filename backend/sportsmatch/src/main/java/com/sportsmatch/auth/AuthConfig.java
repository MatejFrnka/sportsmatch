package com.sportsmatch.auth;

import com.sportsmatch.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

  private final UserRepository userRepository;

/**
* Returns a UserDetailsService that fetches user details by email from a UserRepository.
 * @return UserDetailsService instance.
 * @throws UsernameNotFoundException If the user is not found.
*/
  @Bean
  public UserDetailsService userDetailsService() {
    return username ->
        userRepository
            .findByEmail(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

/**
 * Returns an AuthenticationProvider configured with a custom UserDetailsService
 * and password encoder for authentication.
 * @return AuthenticationProvider instance.
*/
  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());
    return authProvider;
  }

/**
 * Returns an AuthenticationManager based on the provided AuthenticationConfiguration.
 *
 * @param config The AuthenticationConfiguration to configure the AuthenticationManager.
 * @return AuthenticationManager instance.
 * @throws Exception If an exception occurs during configuration.
*/
  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
      return config.getAuthenticationManager();
  }

/**
 * Returns a BCryptPasswordEncoder for password hashing.
 *
 * @return PasswordEncoder instance using BCrypt algorithm.
*/
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}

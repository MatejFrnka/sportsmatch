package com.sportsmatch.controllers;

import com.sportsmatch.auth.AuthService;
import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.services.ValidationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final ValidationService validationService;

  /**
   * Registers a user based on the provided credentials.
   *
   * @param authRequestDTO The authentication request containing user details.
   * @param bindingResult The result of the validation process.
   * @return ResponseEntity indicating the success or failure of the registration. Returns a 400 Bad
   *     Request with validation errors List&lt;String&gt; if input is invalid. Returns a 200 OK
   *     if registration is successful.
   */
  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    authService.register(authRequestDTO);
    return ResponseEntity.ok().build();
  }

  /**
   * Authenticates a user based on the provided credentials.
   *
   * @param authRequestDTO The authentication request containing user credentials.
   * @param bindingResult The result of the validation process.
   * @return ResponseEntity indicating the success or failure of the authentication. Returns a 400
   *     Bad Request with validation errors if input is invalid. Returns a 200 OK with
   *     authentication details if authentication is successful.
   */
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    return ResponseEntity.ok(authService.authenticate(authRequestDTO));
  }
}

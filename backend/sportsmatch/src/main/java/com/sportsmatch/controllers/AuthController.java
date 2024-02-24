package com.sportsmatch.controllers;

import com.sportsmatch.auth.AuthService;
import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.models.User;
import com.sportsmatch.services.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final ValidationService validationService;

  @PostMapping("/register")
  @Tag(name = "Register")
  @Operation(
      summary = "Register new user",
      description = "Register a new user by providing their email and username.")
  public ResponseEntity<?> register(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    try {
      authService.register(authRequestDTO);
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  @PostMapping("/login")
  @Tag(name = "Login")
  @Operation(
      summary = "Login user",
      description = "Login a user by providing their email and username.")
  public ResponseEntity<?> login(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    return ResponseEntity.ok(authService.login(authRequestDTO));
  }

  @GetMapping("/me")
  @Tag(name = "ex.secured endpoint")
  public ResponseEntity<?> getUserMainPage(Authentication authentication) {
    if (authentication == null || !authentication.isAuthenticated()) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
    User user = (User) authentication.getPrincipal();
    UserDTO userDTO = new UserDTO(user.getName());
    return ResponseEntity.ok(userDTO);
  }
}

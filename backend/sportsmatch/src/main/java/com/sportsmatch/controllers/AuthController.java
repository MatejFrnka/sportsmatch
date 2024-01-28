package com.sportsmatch.controllers;

import com.sportsmatch.auth.AuthService;
import com.sportsmatch.dtos.AuthRequestDTO;
import com.sportsmatch.services.ValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    authService.register(authRequestDTO);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/authenticate")
  @Tag(name = "Authenticate")
  @Operation(
      summary = "Authenticate user",
      description = "Authenticate a user by providing their email and username.")
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthRequestDTO authRequestDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    return ResponseEntity.ok(authService.authenticate(authRequestDTO));
  }
}

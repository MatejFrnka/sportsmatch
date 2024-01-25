package com.sportsmatch.controllers;

import com.sportsmatch.auth.AuthService;
import com.sportsmatch.dtos.RequestDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;

  //todo add @Valid to work with validation annotations
  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody RequestDTO requestDTO) {
    return ResponseEntity.ok(authService.register(requestDTO));
  }

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@RequestBody RequestDTO requestDTO) {
    return ResponseEntity.ok(authService.authenticate(requestDTO));
  }
}

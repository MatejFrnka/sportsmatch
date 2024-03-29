package com.sportsmatch.controllers;

import com.sportsmatch.dtos.UserInfoDTO;
import com.sportsmatch.services.UserService;
import com.sportsmatch.services.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

  private final ValidationService validationService;
  private final UserService userService;

  @GetMapping("/get/{username}")
  public ResponseEntity<?> getUser(@PathVariable String username) {
    try {
      return ResponseEntity.ok().body(userService.getUserByName(username));
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  @PostMapping("/update")
  public ResponseEntity<?> updateInfo(
      @RequestBody @Valid UserInfoDTO userInfoDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    try {
      userService.updateUserInfo(userInfoDTO);
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }
}

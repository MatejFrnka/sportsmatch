package com.sportsmatch.controllers;

import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.services.RatingService;
import com.sportsmatch.services.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rating")
public class RatingController {

  private final RatingService ratingService;
  private final ValidationService validationService;

  @PostMapping("/add")
  public ResponseEntity<?> addRating(
      @RequestBody @Valid RatingDTO ratingDTO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest().body(validationService.getAllErrors(bindingResult));
    }
    try {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      ratingService.addRating(ratingDTO, authentication);
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }
}

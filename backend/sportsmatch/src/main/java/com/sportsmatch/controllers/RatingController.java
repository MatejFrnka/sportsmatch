package com.sportsmatch.controllers;

import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.dtos.UserRatingStatsDTO;
import com.sportsmatch.services.RatingService;
import com.sportsmatch.services.ValidationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
      ratingService.addRating(ratingDTO);
      return ResponseEntity.ok().build();
    } catch (ResponseStatusException e) {
      return ResponseEntity.status(e.getStatusCode()).build();
    }
  }

  @GetMapping("/check")
  public ResponseEntity<?> checkRating() {
    return ResponseEntity.ok().body(ratingService.findUnratedEvents());
  }

  @GetMapping("/{id}/summary")
  public ResponseEntity<?> getSummary(@PathVariable Long id) {
    try {
      UserRatingStatsDTO summary = ratingService.getUserRatingStats(id);
      return ResponseEntity.ok().body(summary);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }
}

package com.sportsmatch.controllers;

import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.dtos.UserRatingDTO;
import com.sportsmatch.dtos.UserRatingStatsDTO;
import com.sportsmatch.services.RatingService;
import com.sportsmatch.services.ValidationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
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
  @ApiResponse(content = @Content(schema = @Schema(implementation = UserRatingStatsDTO.class)))
  public ResponseEntity<?> getSummary(@PathVariable Long id) {
    try {
      UserRatingStatsDTO summary = ratingService.getUserRatingStats(id);
      return ResponseEntity.ok().body(summary);
    } catch (Exception e) {
      return ResponseEntity.badRequest().build();
    }
  }

  @GetMapping("/{id}/all")
  public List<UserRatingDTO> getAllByUser(@PathVariable Long id, @ParameterObject Pageable pageable) {
    return ratingService.getAllUserRatings(id, pageable);
  }
}

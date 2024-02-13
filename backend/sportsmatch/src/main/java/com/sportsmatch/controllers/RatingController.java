package com.sportsmatch.controllers;

import com.sportsmatch.dtos.RatingDTO;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    @PostMapping("/add")
    public String addRating(@RequestBody @Valid RatingDTO ratingDTO) {
        // User "rating" is always the authenticated user
        // Opponent "rated"
        // Event "rated" is the event
        // EventRating "rating" is the rating for the game
        // Rating will be in one table

        return "";
    }
}

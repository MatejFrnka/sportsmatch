package com.sportsmatch.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/rating")
public class RatingController {

    @PostMapping("/add")
    public String addRating() {
        // will receive RatingRequestDTO which will @Valid
        // DTO will contain Ratings text and star.
        // DTO will contain "my score and "opponent's score"
        // User "rating" is always the authenticated user
        // Opponent "rated"
        // Event "rated" is the event
        // EventRating "rating" is the rating for the game

        // Rating will be in one table

        return "";
    }
}

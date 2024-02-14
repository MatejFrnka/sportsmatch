package com.sportsmatch.mappers;

import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.models.Rating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingMapper {

    public Rating toUserRatingEntity(RatingDTO ratingDTO) {
        return Rating.builder()
                .starRating(ratingDTO.getUserStarRating())
                .textRating(ratingDTO.getUserTextRating())
                .build();
    }

    public Rating toEventRatingEntity(RatingDTO ratingDTO) {
        return Rating.builder()
                .starRating(ratingDTO.getEventStarRating())
                .build();
    }
}

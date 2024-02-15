package com.sportsmatch.service;

import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.mappers.RatingMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.RatingRepository;
import com.sportsmatch.repositories.UserEventRatingRepository;
import com.sportsmatch.services.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest {

  @Mock private RatingRepository ratingRepository;
  @Mock private UserEventRatingRepository userEventRatingRepository;
  @Mock private EventPlayerRepository eventPlayerRepository;
  @Mock private RatingMapper ratingMapper;
  @InjectMocks private RatingService ratingService;

  @Test
  void addRating() {
    MockitoAnnotations.openMocks(this);
    RatingDTO ratingDTO =
        RatingDTO.builder()
            .userTextRating("it was a long game")
            .userStarRating(2)
            .eventStarRating(2)
            .myScore(10)
            .opponentScore(9)
            .build();

    // Authentication and Player
    Authentication authentication = mock(Authentication.class);
    User player = mock(User.class);
    when(authentication.getPrincipal()).thenReturn(player);

    // EventPlayer
    EventPlayer eventPlayer = mock(EventPlayer.class);
    when(eventPlayerRepository.findEventPlayerByPlayer(any()))
        .thenReturn(Optional.of(eventPlayer));

    // Opponent
    Event event = mock(Event.class);
    when(eventPlayer.getPlayer()).thenReturn(player);
    when(eventPlayer.getEvent()).thenReturn(event);
    when(player.getId()).thenReturn(1L);

    User opponent = mock(User.class);
    EventPlayer opponentEvent = mock(EventPlayer.class);
    when(opponentEvent.getPlayer()).thenReturn(opponent);
    when(opponent.getId()).thenReturn(2L);

    List<EventPlayer> eventPlayers = new ArrayList<>();
    eventPlayers.add(opponentEvent);
    eventPlayers.add(eventPlayer);

    when(eventPlayerRepository.findEventPlayersByEvent(event)).thenReturn(eventPlayers);

    // Mapping
    Rating userRating = mock(Rating.class);
    Rating eventRating = mock(Rating.class);
    when(ratingMapper.toUserRatingEntity(ratingDTO)).thenReturn(userRating);
    when(ratingMapper.toEventRatingEntity(ratingDTO)).thenReturn(eventRating);

    
    ratingService.addRating(ratingDTO, authentication);
  }
  // TODO:
  //  Event Player Behaviour - error: status exception 404 Optional is empty
 //   User Opponent Behaviour
 //   Mapping Behaviour
 //   set Score
 //   Rating Behaviour

  @Test
  void saveUserEventRating() {
    MockitoAnnotations.openMocks(this);

    Authentication authentication = mock(Authentication.class);
    User user = mock(User.class);
    when(authentication.getPrincipal()).thenReturn(user);

    UserEventRating userEventRating = mock(UserEventRating.class);

    userEventRatingRepository.save(userEventRating);
    ratingService.saveUserEventRating(authentication);

    verify(userEventRatingRepository).save(userEventRating);
  }
}


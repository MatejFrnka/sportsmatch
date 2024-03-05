package com.sportsmatch.services;

import com.sportsmatch.BaseTest;
import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.mappers.RatingMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.RatingRepository;
import com.sportsmatch.repositories.UserEventRatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest extends BaseTest {

  @Mock
  private RatingRepository ratingRepository;
  @Mock
  private UserEventRatingRepository userEventRatingRepository;
  @Mock
  private EventPlayerRepository eventPlayerRepository;
  @Mock
  private RatingMapper ratingMapper;
  @Mock
  private UserService userService;
  @InjectMocks
  private RatingService ratingService;

  @Test
  void addRating() {
    RatingDTO ratingDTO =
        RatingDTO.builder()
            .userTextRating("it was a long game")
            .userStarRating(2)
            .eventStarRating(2)
            .myScore(10)
            .opponentScore(9)
            .build();

    // Authentication and Player
    User player = mock(User.class);
    when(userService.getUserFromContext()).thenReturn(player);

    // EventPlayer
    EventPlayer eventPlayer = mock(EventPlayer.class);
    when(eventPlayerRepository.findEventPlayerByPlayer(any())).thenReturn(Optional.of(eventPlayer));

    // Opponent
    Event event = mock(Event.class);
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

    // Score
    doNothing().when(eventPlayer).setMyScore(ratingDTO.getMyScore());
    doNothing().when(eventPlayer).setOpponentScore(ratingDTO.getOpponentScore());
    when(ratingRepository.save(any(Rating.class))).thenReturn(userRating).thenReturn(eventRating);

    // Rating
    UserEventRating userEventRating = mock(UserEventRating.class);
    when(userEventRatingRepository.save(any(UserEventRating.class))).thenReturn(userEventRating);

    ratingService.addRating(ratingDTO);

    verify(ratingRepository, times(2)).save(any(Rating.class));
    verify(userEventRatingRepository, times(1)).save(any(UserEventRating.class));
    verify(eventPlayer).setMyScore(ratingDTO.getMyScore());
    verify(eventPlayer).setOpponentScore(ratingDTO.getOpponentScore());
  }
}

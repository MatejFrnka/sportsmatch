package com.sportsmatch.services;

import com.sportsmatch.BaseTest;
import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.dtos.UserRatingStatsDTO;
import com.sportsmatch.mappers.EventMapper;
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

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RatingServiceTest extends BaseTest {

  @Mock private RatingRepository ratingRepository;
  @Mock private UserEventRatingRepository userEventRatingRepository;
  @Mock private EventPlayerRepository eventPlayerRepository;
  @Mock private RatingMapper ratingMapper;
  @Mock private UserService userService;
  @InjectMocks private RatingService ratingService;
  @Mock private EventMapper eventMapper;

  @Test
  void addRating() {
    RatingDTO ratingDTO =
        RatingDTO.builder()
            .userTextRating("it was a long game")
            .userStarRating(2)
            .eventStarRating(2)
            .myScore(10)
            .opponentScore(9)
            .eventId(1L)
            .build();

    // Authentication and Player
    User player = mock(User.class);
    when(userService.getUserFromContext()).thenReturn(player);

    // EventPlayer
    EventPlayer eventPlayer = mock(EventPlayer.class);
    when(eventPlayerRepository.findEventPlayerByPlayerAndEventId(any(), any()))
        .thenReturn(Optional.of(eventPlayer));

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

  @Test
  void testFindUnratedEventsWithUnratedEvent() {
    // Arrange:
    // Authentication and Player
    User player = new User();
    player.setId(1L);
    when(userService.getUserFromContext()).thenReturn(player);

    // EventPlayers
    EventPlayer eventPlayer1 = mock(EventPlayer.class);
    EventPlayer eventPlayer2 = mock(EventPlayer.class);
    Set<EventPlayer> eventPlayers = new HashSet<>();
    eventPlayers.add(eventPlayer1);
    eventPlayers.add(eventPlayer2);

    // Event
    List<Event> pastEvents = new ArrayList<>();
    Event event = new Event();
    event.setPlayers(eventPlayers);

    // Find player's past events
    pastEvents.add(event);
    when(eventPlayerRepository.findPastEventsByPlayer(anyLong(), any(LocalDateTime.class)))
        .thenReturn(pastEvents);

    // Check if event is rated
    when(userEventRatingRepository.existsByPlayerAndEvent(any(User.class), any(Event.class)))
        .thenReturn(false);

    // Mapping
    EventDTO eventDTO = new EventDTO();
    when(eventMapper.convertEventToEventDTO(event)).thenReturn(eventDTO);

    // Act:
    List<EventDTO> unratedEvents = ratingService.findUnratedEvents();

    // Assert:
    assertEquals(1, unratedEvents.size());
  }

  @Test
  void testFindUnratedEventsWithRatedEvent() {
    // Arrange:
    // Authentication and Player
    User player = new User();
    player.setId(1L);
    when(userService.getUserFromContext()).thenReturn(player);

    // Event
    List<Event> pastEvents = new ArrayList<>();
    when(eventPlayerRepository.findPastEventsByPlayer(anyLong(), any(LocalDateTime.class)))
        .thenReturn(pastEvents);

    // Act:
    List<EventDTO> unratedEvents = ratingService.findUnratedEvents();

    // Assert:
    assertEquals(0, unratedEvents.size());
  }

  @Test
  void getUserRatingStatsReturnsCorrectAverageRating() {
    // Arrange:
    // Average rating
    when(userEventRatingRepository.findAverageRating(anyLong())).thenReturn(Optional.of(2.6666));

    // Act
    UserRatingStatsDTO result = ratingService.getUserRatingStats(1L);

    // Assert
    assertEquals(2.7, result.getAverageRating());
  }

  @Test
  void getUserRatingStatsReturnsMapOfStarRatings() {
    // Arrange:
    // Rating counts
    List<Object[]> counts = new ArrayList<>();
    counts.add(new Object[] {2, 2L});
    counts.add(new Object[] {1, 1L});
    when(userEventRatingRepository.findRatingsCount(anyLong())).thenReturn(counts);

    // Act
    UserRatingStatsDTO result = ratingService.getUserRatingStats(1L);

    // Assert
    assertEquals(1, result.getStarRatingCounts().get("1"));
    assertEquals(2, result.getStarRatingCounts().get("2"));
    assertEquals(0, result.getStarRatingCounts().get("3"));
    assertEquals(0, result.getStarRatingCounts().get("4"));
    assertEquals(0, result.getStarRatingCounts().get("5"));
  }
}

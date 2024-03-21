package com.sportsmatch.services;

import com.sportsmatch.BaseTest;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.EventStatusOptions;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest extends BaseTest {
  @Mock private EventRepository eventRepository;
  @Mock private EventPlayerRepository eventPlayerRepository;
  @Mock private UserService userService;
  @InjectMocks private EventService eventService;
  private User loggedUser;
  private User otherUser;

  @BeforeEach
  void setUp() {
    //    userService = mock(UserService.class);
    //    eventService = new EventService(userService);
    loggedUser = new User();
    loggedUser = createUser("loggedUser");
    otherUser = new User();
    otherUser = createUser("otherUser");
  }

  // Create an user
  private User createUser(String name) {
    User user = new User();
    user.setName(name);
    return user;
  }

  // Create an event player
  private EventPlayer createEventPlayer(User player, Integer myScore, Integer opponentScore) {
    EventPlayer eventPlayer = new EventPlayer();
    eventPlayer.setPlayer(player);
    eventPlayer.setMyScore(myScore);
    eventPlayer.setOpponentScore(opponentScore);
    return eventPlayer;
  }

  @Test
  void checkScoreMatchExpectStatus_MATCH() {
    // ARRANGE:

    // Event players
    EventPlayer loggedEventPlayer = createEventPlayer(loggedUser, 2, 2);
    EventPlayer otherEventPlayer = createEventPlayer(otherUser, 2, 2);

    // Set of the eventPlayers
    Set<EventPlayer> eventPlayers = new HashSet<>();
    eventPlayers.add(loggedEventPlayer);
    eventPlayers.add(otherEventPlayer);

    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Act:
    EventStatusOptions result = eventService.checkScoreMatch(eventPlayers);

    // Assert:
    assertEquals(EventStatusOptions.MATCH, result);
  }

  @Test
  void checkScoreMatchExpectStatus_MISMATCH() {
    // ARRANGE:

    // Event players
    EventPlayer loggedEventPlayer = createEventPlayer(loggedUser, 3, 2);
    EventPlayer otherEventPlayer = createEventPlayer(otherUser, 2, 5);

    // Set of the eventPlayers
    Set<EventPlayer> eventPlayers = new HashSet<>();
    eventPlayers.add(loggedEventPlayer);
    eventPlayers.add(otherEventPlayer);

    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Act:
    EventStatusOptions result = eventService.checkScoreMatch(eventPlayers);

    // Assert:
    assertEquals(EventStatusOptions.MISMATCH, result);
  }

  @Test
  void checkScoreMatchExpectStatus_WAITING_FOR_RATING() {
    // ARRANGE:

    // Event players
    EventPlayer loggedEventPlayer = createEventPlayer(loggedUser, 3, 2);
    EventPlayer otherEventPlayer = createEventPlayer(otherUser, null, null);

    // Set of the eventPlayers
    Set<EventPlayer> eventPlayers = new HashSet<>();
    eventPlayers.add(loggedEventPlayer);
    eventPlayers.add(otherEventPlayer);

    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Act:
    EventStatusOptions result = eventService.checkScoreMatch(eventPlayers);

    // Assert:
    assertEquals(EventStatusOptions.WAITING_FOR_RATING, result);
  }

  @Test
  void checkScoreMatchExpectStatus_INVALID_PLAYER() {
    // ARRANGE:

    // Event players
    EventPlayer loggedEventPlayer = createEventPlayer(loggedUser, 3, 2);

    // Set of the eventPlayers
    Set<EventPlayer> eventPlayers = new HashSet<>();
    eventPlayers.add(loggedEventPlayer);

    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Act:
    EventStatusOptions result = eventService.checkScoreMatch(eventPlayers);

    // Assert:
    assertEquals(EventStatusOptions.INVALID_PLAYER, result);
  }

  @Test
  void joinEventAddsUserToEvent() {
    // Arrange:
    // User:
    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Event
    Event event = new Event();
    event.setId(1L);
    when(eventRepository.findEventById(1L)).thenReturn(Optional.of(event));

    // EventPlayer
    when(eventPlayerRepository.findEventPlayerByEventAndPlayer(event, loggedUser))
        .thenReturn(Optional.empty());
    EventPlayer eventPlayer = new EventPlayer();
    eventPlayer.setEvent(event);
    eventPlayer.setPlayer(loggedUser);

    // Act:
    try {
      eventService.joinEvent(1L);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    // Assert
    verify(eventPlayerRepository, times(1)).save(any(EventPlayer.class));
  }

  @Test
  void joinEventThrowExceptionWhenEventIsFull() {
    // Arrange:
    // User:
    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Event
    Event event = new Event();
    event.setId(1L);
    when(eventRepository.findEventById(1L)).thenReturn(Optional.of(event));

    // Add players to Event
    EventPlayer player1 = new EventPlayer();
    EventPlayer player2 = new EventPlayer();
    event.getPlayers().add(player1);
    event.getPlayers().add(player2);

    // Assert:
    assertThrows(Exception.class, () -> eventService.joinEvent(1L));
  }

  @Test
  void joinEventThrowsExceptionWhenUserHasAlreadyJoined() {
    // Arrange:
    // User:
    when(userService.getUserFromContext()).thenReturn(loggedUser);

    // Event
    Event event = new Event();
    event.setId(1L);
    when(eventRepository.findEventById(1L)).thenReturn(Optional.of(event));

    // EventPlayer
    EventPlayer eventPlayer = new EventPlayer();
    eventPlayer.setEvent(event);
    eventPlayer.setPlayer(loggedUser);
    when(eventPlayerRepository.findEventPlayerByEventAndPlayer(event, loggedUser))
            .thenReturn(Optional.of(eventPlayer));

    // Assert:
    assertThrows(Exception.class, () -> eventService.joinEvent(1L));
  }
}

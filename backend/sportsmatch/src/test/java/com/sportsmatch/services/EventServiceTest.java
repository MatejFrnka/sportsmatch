package com.sportsmatch.services;

import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.EventStatusOptions;
import com.sportsmatch.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventServiceTest {
  private UserService userService;
  private EventService eventService;
  private User loggedUser;
  private User otherUser;

  @BeforeEach
  void setUp() {
    userService = mock(UserService.class);
    eventService = new EventService(userService);
    loggedUser = createUser("loggedUser");
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

    when(userService.getUserFromTheSecurityContextHolder()).thenReturn(loggedUser);

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

    when(userService.getUserFromTheSecurityContextHolder()).thenReturn(loggedUser);

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

    when(userService.getUserFromTheSecurityContextHolder()).thenReturn(loggedUser);

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

    when(userService.getUserFromTheSecurityContextHolder()).thenReturn(loggedUser);

    // Act:
    EventStatusOptions result = eventService.checkScoreMatch(eventPlayers);

    // Assert:
    assertEquals(EventStatusOptions.INVALID_PLAYER, result);
  }
}
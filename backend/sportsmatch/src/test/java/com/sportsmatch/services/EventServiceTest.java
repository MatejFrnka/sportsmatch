package com.sportsmatch.services;

import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.EventStatusOptions;
import com.sportsmatch.models.User;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EventServiceTest {

  private final UserService userService = mock(UserService.class);
  private final EventService eventService = new EventService(userService);

  @Test
  void checkScoreMatchExpectStatus_MATCH() {
    // ARRANGE:

    // Logged user
    User loggedUser = new User();
    loggedUser.setName("loggedUser");

    // Other user
    User otherUser = new User();
    otherUser.setName("otherUser");

    // Logged event player
    EventPlayer loggedEventPlayer = new EventPlayer();
    loggedEventPlayer.setPlayer(loggedUser);
    loggedEventPlayer.setMyScore(2);
    loggedEventPlayer.setOpponentScore(2);

    // other event player
    EventPlayer otherEventPlayer = new EventPlayer();
    otherEventPlayer.setPlayer(otherUser);
    otherEventPlayer.setMyScore(2);
    otherEventPlayer.setOpponentScore(2);

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

    // Logged user
    User loggedUser = new User();
    loggedUser.setName("loggedUser");

    // Other user
    User otherUser = new User();
    otherUser.setName("otherUser");

    // Logged event player
    EventPlayer loggedEventPlayer = new EventPlayer();
    loggedEventPlayer.setPlayer(loggedUser);
    loggedEventPlayer.setMyScore(5);
    loggedEventPlayer.setOpponentScore(2);

    // other event player
    EventPlayer otherEventPlayer = new EventPlayer();
    otherEventPlayer.setPlayer(otherUser);
    otherEventPlayer.setMyScore(2);
    otherEventPlayer.setOpponentScore(3);

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

    // Logged user
    User loggedUser = new User();
    loggedUser.setName("loggedUser");

    // Other user
    User otherUser = new User();
    otherUser.setName("otherUser");

    // Logged event player
    EventPlayer loggedEventPlayer = new EventPlayer();
    loggedEventPlayer.setPlayer(loggedUser);
    loggedEventPlayer.setMyScore(3);
    loggedEventPlayer.setOpponentScore(2);

    // other event player
    EventPlayer otherEventPlayer = new EventPlayer();
    otherEventPlayer.setPlayer(otherUser);
    otherEventPlayer.setMyScore(null);
    otherEventPlayer.setOpponentScore(null);

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

    // Logged user
    User loggedUser = new User();
    loggedUser.setName("loggedUser");

    // Logged event player
    EventPlayer loggedEventPlayer = new EventPlayer();
    loggedEventPlayer.setPlayer(loggedUser);
    loggedEventPlayer.setMyScore(3);
    loggedEventPlayer.setOpponentScore(2);

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
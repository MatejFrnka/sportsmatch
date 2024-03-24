package com.sportsmatch.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.sportsmatch.BaseTest;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
class RankServiceTest extends BaseTest {

  @Mock EventRepository eventRepository;
  @InjectMocks RankService rankService;

  @Test
  void updatePlayersRanks() {

    // User
    User firstUser = new User();
    firstUser.setRank(1000);
    firstUser.setId(1L);

    User secondUser = new User();
    secondUser.setRank(1000);
    secondUser.setId(2L);

    // EventPlayer
    EventPlayer firstPlayer = new EventPlayer();
    firstPlayer.setId(1L);
    firstPlayer.setMyScore(5);
    firstPlayer.setOpponentScore(2);
    firstPlayer.setPlayer(firstUser);

    EventPlayer secondPlayer = new EventPlayer();
    secondPlayer.setId(2L);
    secondPlayer.setMyScore(2);
    secondPlayer.setOpponentScore(5);
    secondPlayer.setPlayer(secondUser);

    // Event
    Event event = new Event();
    event.setId(1L);
    event.getPlayers().add(firstPlayer);
    event.getPlayers().add(secondPlayer);

    rankService.updatePlayersRanks(event);

    assertEquals(1012, firstUser.getRank());
    assertEquals(988, secondUser.getRank());
    assertTrue(event.getIsRanksUpdated());
    verify(eventRepository, times(1)).save(event);
  }
}

package com.sportsmatch.services;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RankService {

  @Value("${app.sportsmingle.num-game-threshold}")
  private int[] NUM_GAME_THRESHOLD;

  @Value("${app.sportsmingle.k-factors}")
  private double[] K_FACTORS;

  @Value("${app.sportsmingle.k-factor-default}")
  private double DEFAULT_K_FACTOR;

  private final EventService eventService;
  private final EventRepository eventRepository;

  public void updatePlayersRanks(Event event) {
    if (!eventService.isValidEvent(event)
        || event.getIsRanksUpdated()
        || event.getPlayers().size() < 2) {
      return;
    }

    List<EventPlayer> players = new ArrayList<>(event.getPlayers());
    EventPlayer firstPlayer = players.get(0);
    EventPlayer secondPlayer = players.get(1);

    firstPlayer.getPlayer().setRank(newRating(firstPlayer, secondPlayer));
    secondPlayer.getPlayer().setRank(newRating(secondPlayer, firstPlayer));
    event.setIsRanksUpdated(true);

    eventRepository.save(event);
  }

  private double calculateTransformedRating(User user) {
    return Math.pow(10, ((double) user.getRank() / 400));
  }

  private double expectedScore(User firstPlayer, User secondPlayer) {
    double firstTransformedRating = calculateTransformedRating(firstPlayer);
    double secondTransformedRating = calculateTransformedRating(secondPlayer);
    return firstTransformedRating / (firstTransformedRating + secondTransformedRating);
  }

  private int newRating(EventPlayer firstPlayer, EventPlayer secondPlayer) {
    double updatedRating =
        firstPlayer.getPlayer().getRank()
            + adjustKFactor(firstPlayer.getPlayer())
                * ((getScore(firstPlayer))
                    - expectedScore(firstPlayer.getPlayer(), secondPlayer.getPlayer()));
    return (int) Math.round(updatedRating);
  }

  private double getScore(EventPlayer eventPlayer) {
    double myScore = eventPlayer.getMyScore();
    double opponentScore = eventPlayer.getOpponentScore();
    User player = eventPlayer.getPlayer();

    player.setTotalPlayed(player.getTotalPlayed() + 1);

    if (myScore > opponentScore) {
      player.setWin(player.getWin() + 1);
      return 1;
    } else if (myScore == opponentScore) {
      return 0.5;
    } else {
      player.setLoss(player.getLoss() + 1);
      return 0;
    }
  }

  private double adjustKFactor(User user) {
    for (int i = 0; i < NUM_GAME_THRESHOLD.length; i++) {
      if (user.getTotalPlayed() <= NUM_GAME_THRESHOLD[i]) {
        return K_FACTORS[i];
      }
    }
    return DEFAULT_K_FACTOR;
  }
}

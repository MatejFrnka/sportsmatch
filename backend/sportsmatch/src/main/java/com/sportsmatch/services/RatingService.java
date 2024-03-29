package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.RatingDTO;
import com.sportsmatch.mappers.EventMapper;
import com.sportsmatch.mappers.RatingMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RatingService {

  private final RatingRepository ratingRepository;
  private final UserEventRatingRepository userEventRatingRepository;
  private final EventPlayerRepository eventPlayerRepository;
  private final RatingMapper ratingMapper;
  private final UserService userService;
  private final EventMapper eventMapper;

  public void addRating(RatingDTO ratingDTO) {
    User player = userService.getUserFromContext();
    EventPlayer eventPlayer = getEventPlayer(player);
    User opponent = findOpponent(eventPlayer, player);
    Rating userRating = ratingMapper.toUserRatingEntity(ratingDTO);
    Rating eventRating = ratingMapper.toEventRatingEntity(ratingDTO);

    eventPlayer.setMyScore(ratingDTO.getMyScore());
    eventPlayer.setOpponentScore(ratingDTO.getOpponentScore());

    UserEventRating userEventRating =
        UserEventRating.builder()
            .userRating(userRating)
            .eventRating(eventRating)
            .player(player)
            .opponent(opponent)
            .event(eventPlayer.getEvent())
            .build();
    ratingRepository.save(userRating);
    ratingRepository.save(eventRating);
    userEventRatingRepository.save(userEventRating);
  }

  private EventPlayer getEventPlayer(User player) {
    Optional<EventPlayer> eventPlayerOptional =
        eventPlayerRepository.findEventPlayerByPlayer(player);
    return eventPlayerOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  private User findOpponent(EventPlayer eventPlayer, User player) {
    List<EventPlayer> eventPlayers =
        eventPlayerRepository.findEventPlayersByEvent(eventPlayer.getEvent());
    return eventPlayers.stream()
        .map(EventPlayer::getPlayer)
        .filter(p -> !p.getId().equals(player.getId()))
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
  }

  public List<EventDTO> findUnratedEvents() {
    User player = userService.getUserFromContext();
    List<EventPlayer> playersEvents = eventPlayerRepository.findEventPlayersByPlayer(player);
    List<Event> unratedEvents =
        playersEvents.stream()
            .map(EventPlayer::getEvent)
            .filter(e -> !isEventRated(e, player))
            .toList();

    return unratedEvents.stream().map(eventMapper::convertEventToEventDTO).toList();
  }

  private boolean isEventRated(Event event, User player) {
    return userEventRatingRepository.findUserEventRatingByEventAndPlayer(event, player).isPresent();
  }
}

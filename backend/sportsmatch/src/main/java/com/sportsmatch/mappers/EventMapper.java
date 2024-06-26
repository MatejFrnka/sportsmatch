package com.sportsmatch.mappers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.dtos.HostEventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.EventStatusOptions;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventMapper {

  private final UserMapper userMapper;
  private ModelMapper modelMapper;

  private PlaceMapper placeMapper;

  @Autowired
  public EventMapper(ModelMapper modelMapper, UserMapper userMapper, PlaceMapper placeMapper) {
    this.modelMapper = modelMapper;
    this.userMapper = userMapper;
    this.placeMapper = placeMapper;
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public EventDTO convertEventToEventDTO(Event event) {
    EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
    List<EventPlayer> eventPlayers = event.getPlayers().stream().toList();
    if (eventPlayers.size() > 0) {
      eventDTO.setPlayer1Id(eventPlayers.get(0).getPlayer().getId());
      eventDTO.setPlayer1Name(eventPlayers.get(0).getPlayer().getName());
    }
    if (eventPlayers.size() > 1) {
      eventDTO.setPlayer2Id(eventPlayers.get(1).getPlayer().getId());
      eventDTO.setPlayer2Name(eventPlayers.get(1).getPlayer().getName());
    }
    eventDTO.setSport(event.getSport().getName());
    eventDTO.setPlaceDTO(placeMapper.toDTO(event.getPlace()));
    return eventDTO;
  }

  public Event convertHostEventDTOtoEvent(HostEventDTO hostEventDTO) {
    modelMapper.typeMap(EventDTO.class, Event.class).addMappings(e -> e.skip(Event::setId));
    return modelMapper.map(hostEventDTO, Event.class);
  }

  /**
   * Convert an Event entity to and EventHistoryDTO.
   *
   * @param event to be converted
   * @return an EventHistoryDTO based on the given Event
   */
  public EventHistoryDTO toDTO(Event event, String loggedUsername, EventStatusOptions status) {

    // Get the logged-in EventPlayer
    EventPlayer loggedPlayer = event.getPlayers().stream()
        .filter(p -> p.getPlayer().getName().equals(loggedUsername))
        .findFirst()
        .orElse(null);

    // Get the opponent EventPlayer
    EventPlayer opponentPlayer = event.getPlayers().stream()
        .filter(p -> !p.getPlayer().getName().equals(loggedUsername))
        .findFirst()
        .orElse(null);


    return EventHistoryDTO.builder()
        .userScore((loggedPlayer != null) ? loggedPlayer.getMyScore() : null)
        .opponentScore((opponentPlayer != null) ? opponentPlayer.getMyScore() : null)
        .opponent((opponentPlayer != null) ? userMapper.toDTO(opponentPlayer.getPlayer()) : null)
        .dateOfTheMatch(event.getDateEnd())
        .status(status)
        .build();
  }
}
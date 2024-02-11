package com.sportsmatch.mappers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.dtos.UserDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.User;
import com.sportsmatch.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EventMapper {

  private ModelMapper modelMapper;
  private final UserService userService;

  @Autowired
  public EventMapper(ModelMapper modelMapper, UserService userService) {
    this.modelMapper = modelMapper;
    this.userService = userService;
    this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
  }

  public EventDTO convertEventToEventDTO(Event event) {
    EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
    List<EventPlayer> eventPlayers = event.getPlayers().stream().toList();
    if (eventPlayers.size() > 0) {
      eventDTO.setPlayer1Id(eventPlayers.get(0).getPlayer().getId());
    }
    if (eventPlayers.size() > 1) {
      eventDTO.setPlayer2Id(eventPlayers.get(1).getPlayer().getId());
    }
    eventDTO.setSport(event.getSport().getName());
    return eventDTO;
  }

  public Event convertEventDTOtoEvent(EventDTO eventDTO) {
    modelMapper.typeMap(EventDTO.class, Event.class).addMappings(e -> e.skip(Event::setId));
    return modelMapper.map(eventDTO, Event.class);
  }

  //TODO: finish the converting method
  public EventHistoryDTO toDTO(Event event) {

    User loggedUser = userService.getUserFromTheSecurityContextHolder();

    Integer userScore = null;
    Integer opponentScore = null;
    UserDTO opponent = null;
    LocalDateTime dateOfTheMatch = event.getDateEnd();
    String status = null;

    return EventHistoryDTO.builder()
        .userScore(userScore)
        .opponentScore(opponentScore)
        .opponent(opponent)
        .dateOfTheMatch(dateOfTheMatch)
        .status(status)
        .build();
  }
}

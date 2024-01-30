package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.mappers.EventMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.EventRepository;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EventService {
  private EventRepository eventRepository;
  private EventMapper eventMapper;
  private UserRepository userRepository;
  private SportRepository sportRepository;
  private EventPlayerRepository eventPlayerRepository;

  public Event getEventById(Long id) {
    return eventRepository
        .findEventById(id)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
  }

  public EventDTO getEventDTObyEventId(Long id) {
    Event event =
        eventRepository
            .findEventById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    EventDTO eventDTO = eventMapper.convertEventToEventDTO(event);
    return eventDTO;
  }

  public List<EventDTO> getAllEvents() {
    List<Event> eventList = eventRepository.findAll();
    List<EventDTO> eventDTOList = new ArrayList<>();
    for (Event event : eventList) {
      eventDTOList.add(getEventDTObyEventId(event.getId()));
    }
    return eventDTOList;
  }

  public EventPlayer addPlayerToEvent(Long playerId, Long eventId) {
    EventPlayer eventPlayer = new EventPlayer();
    eventPlayer.setPlayer(
        userRepository
            .findUserById(playerId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    eventPlayer.setEvent(
        eventRepository
            .findEventById(eventId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    return eventPlayerRepository.save(eventPlayer);
  }

  public Event createEvent(EventDTO eventDTO) {
    Event newEvent = eventMapper.convertEventDTOtoEvent(eventDTO);
    newEvent.setSport(
        sportRepository
            .findSportByName(eventDTO.getSport())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
    newEvent = eventRepository.save(newEvent);

    Set<EventPlayer> players = new HashSet<>();
    if (eventDTO.getPlayer1Id() != null) {
      players.add(addPlayerToEvent(eventDTO.getPlayer1Id(), newEvent.getId()));
    }
    if (eventDTO.getPlayer2Id() != null) {
      players.add(addPlayerToEvent(eventDTO.getPlayer2Id(), newEvent.getId()));
    }

    newEvent.setPlayers(players);
    return newEvent;
  }

  public void deleteEventFromDatabase(Event eventById) {
    eventRepository.deleteById(eventById.getId());
  }
}

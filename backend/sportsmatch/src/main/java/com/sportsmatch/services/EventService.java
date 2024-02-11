package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.mappers.EventMapper;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.EventRepository;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
  private final UserService userService;
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

  public List<EventDTO> getEventsBySports(List<Long> sportsIds) {
    List<EventDTO> eventDTOList = new ArrayList<>();
    List<Event> eventListBySport = eventRepository.findAllBySportIdIn(sportsIds);
    for (Event event : eventListBySport) {
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


  /**
   * Retrieves the event history of the logged-in user.
   *
   * @param pageable pagination information (page, size)
   * @return a list of EventHistoryDTOs representing the logged-in user's event history
   */
  public List<EventHistoryDTO> getEventsHistory(final Pageable pageable) {
    return eventRepository.findEventsByUser(userService.getUserFromTheSecurityContextHolder(), LocalDateTime.now(), pageable)
        .stream()
        .map(eventMapper::toDTO)
        .collect(Collectors.toList());
  }


  /**
   * Returns the checked status of the match (score, voting)
   *
   * @param players who entered the event
   * @return the status of the match
   * - both users submitted their ratings and it's matches   -> MATCH
   * - users submitted different rating    -> DISMATCH
   * - the other users hasn’t submitted rating yet   -> WAITING FOR RATING
   */
  public String checkTheStatusOfTheEvent(Set<EventPlayer> players) {

    return null;
  }
}

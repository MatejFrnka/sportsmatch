package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.dtos.HostEventDTO;
import com.sportsmatch.dtos.RequestEventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/event")
public class EventsController {

  private final EventService eventService;

  public EventsController(EventService eventService) {
    this.eventService = eventService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
    EventDTO eventDTO = eventService.getEventDTObyEventId(id);
    return ResponseEntity.ok().body(eventDTO);
  }

  @PostMapping("")
  public ResponseEntity<?> addEvent(@RequestBody @Valid HostEventDTO hostEventDTO) {
    Event newEvent = eventService.createEvent(hostEventDTO);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(eventService.getEventDTObyEventId(newEvent.getId()));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
    Event eventById = eventService.getEventById(id);
    eventService.deleteEventFromDatabase(eventById);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @GetMapping("/upcoming-events")
  public ResponseEntity<?> getUpcomingEvents(@RequestBody List<Long> sportsIds) {
    List<EventDTO> listOfEvents = eventService.getEventsBySports(sportsIds);
    return ResponseEntity.ok().body(listOfEvents);
  }

  /**
   * This endpoint returns the history of the finished events by the logged-in user.
   *
   * @param pageable it contains the page and size for pagination
   * @return a list of finished EventHistoryDTO of the logged-in user
   */
  @GetMapping("/event-history")
  public List<EventHistoryDTO> getEventsHistory(@ParameterObject final Pageable pageable) {
    return eventService.getEventsHistory(pageable);
  }

  /**
   * This endpoint returns list of Events sorted by distance from the given location. User can
   * filter by sports.
   *
   * @param requestEventDTO it contains longitude and latitude and a list of sports for filter if
   *     given
   * @param pageable it contains the page and size for pagination
   * @return a list of Events sorted by distance from the given location. User can filter by sports.
   */
  @GetMapping("/nearby")
  public List<EventDTO> getNearbyEvents(
          @ParameterObject RequestEventDTO requestEventDTO, @ParameterObject final Pageable pageable) {
    return eventService.getNearbyEvents(requestEventDTO, pageable);
  }

  @PostMapping("/{id}/join")
  public ResponseEntity<?> joinEvent(@PathVariable("id") Long id) {
    try {
      eventService.joinEvent(id);
      return ResponseEntity.ok().build();
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  /**
   * This endpoint returns the upcoming matches of the logged-in user.
   *
   * @return a list of logged-in user's upcoming EventDTOs ordered by date ascending
   */
  @GetMapping("/upcoming-matches")
  public List<EventDTO> getUpcomingMatches() {
    return eventService.getUsersUpcomingEvents();
  }
}

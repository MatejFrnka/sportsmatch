package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.dtos.RequestEventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import jakarta.validation.Valid;
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
  public ResponseEntity<?> addEvent(@RequestBody @Valid EventDTO eventDTO) {
    Event newEvent = eventService.createEvent(eventDTO);
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
  public List<EventHistoryDTO> getEventsHistory(final Pageable pageable) {
    return eventService.getEventsHistory(pageable);
  }

  @GetMapping("/nearby")
  public ResponseEntity<?> getNearbyEvents(RequestEventDTO requestEventDTO) {
    // mock endpoint currently assumes no sports are supplied returning all events
    return ResponseEntity.ok().body(eventService.filterEvent(requestEventDTO));
  }
}

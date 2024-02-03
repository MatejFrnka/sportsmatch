package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import jakarta.validation.Valid;
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

  // todo this is placeholder for frontend - it doesn't do anything yet
  @GetMapping("/event-history")
  public EventHistoryDTO getEventsHistory(){
    EventHistoryDTO eventHistoryDTO = new EventHistoryDTO();
    return eventHistoryDTO;
  }
}

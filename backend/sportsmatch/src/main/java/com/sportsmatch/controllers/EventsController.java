package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(eventService.getEventDTObyEventId(newEvent.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {
        Event eventById = eventService.getEventById(id);
        eventService.deleteEventFromDatabase(eventById);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
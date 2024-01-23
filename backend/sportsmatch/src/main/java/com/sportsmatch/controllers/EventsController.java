package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EventsController {

    private EventService eventService;


    public EventsController(EventService eventService) {
        this.eventService = eventService;

    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable("id") Long id) {
        Event event = eventService.getEventById(id);
        EventDTO eventDTO = new EventDTO(event);
        return ResponseEntity.ok().body(eventDTO);
    }

    // todo Post
    @PostMapping("/event")
    public ResponseEntity<?> addEvent(@RequestBody @Valid EventDTO eventDTO) {
        Event newEvent = eventService.createEvent(eventDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new EventDTO(newEvent));
    }

    @DeleteMapping("/event/{id}")
    public ResponseEntity<?> deleteEvent(@PathVariable("id") Long id) {

        try {
            Event eventById = eventService.getEventById(id);
            if (eventById != null) {
                eventService.deleteEventFromDatabase(eventById);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid value");
    }

//      todo Put - what values should be updated?
//    @PutMapping("/event/{id}")
//    public ResponseEntity<?> updateEvent(){
//
//        return new ResponseEntity<>();
//    }
}

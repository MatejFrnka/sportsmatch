package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.services.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class EventsController {

    private EventService eventService;


    public EventsController(EventService eventService) {
        this.eventService = eventService;

    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable ("id") Long id){
        try {
            Event event = eventService.getEventById(id);
            if(event != null) {
                EventDTO eventDTO = new EventDTO(event);
                return ResponseEntity.ok().body(eventDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid data");
        }
    }
    // todo Post
    @PostMapping("/event")
    public ResponseEntity<?> addEvent(@RequestBody EventDTO eventDTO){
        try {
            Event newEvent = eventService.createEvent(eventDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(eventService.getAllEvents());
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Invalid data. Event was not created.");
        }
    }
//      todo Delete
//    @DeleteMapping("/event/{id}")
//    public ResponseEntity<?> deleteEvent(){
//
//        return new ResponseEntity<>();
//    }
//      todo Put
//    @PutMapping("/event/{id}")
//    public ResponseEntity<?> updateEvent(){
//
//        return new ResponseEntity<>();
//    }
}
package com.sportsmatch.controllers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.services.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EventsController {

    private EventService eventService;


    public EventsController(EventService eventService
                            ) {
        this.eventService = eventService;

    }

    @GetMapping("/event/{id}")
    public ResponseEntity<?> getEvent(@PathVariable ("id") Long id){
        EventDTO eventDTO = new EventDTO(eventService.getEventById(id));
        return ResponseEntity.ok().body(eventDTO);
    }
//      todo Post
//    @PostMapping("/v1/event")
//    public ResponseEntity<?> addEvent(){
//
//        return new ResponseEntity<>();
//    }
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

package com.sportsmatch.services;

import com.sportsmatch.models.Event;
import com.sportsmatch.repos.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository){
        this.eventRepository = eventRepository;
    }

    public Event getEventById(Long id){
        return eventRepository.findEventById(id);
    }
}

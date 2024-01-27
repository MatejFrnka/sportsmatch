package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.mappers.EventMapper;
import com.sportsmatch.models.*;
import com.sportsmatch.repos.EventRepository;
import com.sportsmatch.repos.SportRepository;
import com.sportsmatch.repos.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class EventService {
    private EventRepository eventRepository;
    private EventMapper eventMapper;

    public Event getEventById(Long id) {
        return eventRepository.findEventById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST));
    }

    public EventDTO getEventDTObyEventId(Long id){
        Event event = eventRepository.findEventById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST));
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

    public Event createEvent(EventDTO eventDTO) {
        Event newEvent = eventMapper.convertEventDTOtoEvent(eventDTO);
        eventRepository.save(newEvent);
        return newEvent;
    }

    public void deleteEventFromDatabase(Event eventById) {
        eventRepository.deleteById(eventById.getId());
    }
}

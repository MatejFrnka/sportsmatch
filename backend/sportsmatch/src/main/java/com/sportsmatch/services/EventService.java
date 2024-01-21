package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.Sport;
import com.sportsmatch.repos.EventPlayerRepository;
import com.sportsmatch.repos.EventRepository;
import com.sportsmatch.repos.SportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventService {

    private EventRepository eventRepository;
    private SportRepository sportRepository;
    private EventPlayerRepository eventPlayerRepository;

    @Autowired
    public EventService(EventRepository eventRepository,
                        SportRepository sportRepository,
                        EventPlayerRepository eventPlayerRepository) {
        this.eventRepository = eventRepository;
        this.sportRepository = sportRepository;
        this.eventPlayerRepository = eventPlayerRepository;

    }

    public Event getEventById(Long id) {
        return eventRepository.findEventById(id);
    }

    public List<EventDTO> getAllEvents(){
        List<Event> eventList = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();
        for (Event event : eventList) {
            eventDTOList.add(new EventDTO(event));
        }
        return eventDTOList;
    }

    public Event createEvent(EventDTO eventDTO) {
        Event newEvent = new Event();
        if (isValid(eventDTO)) {
            newEvent.setDateStart(eventDTO.getDateStart());
            newEvent.setDateEnd(eventDTO.getDateEnd());
            newEvent.setLocation(eventDTO.getLocation());
            newEvent.setMinElo(eventDTO.getMinElo());
            newEvent.setMaxElo(eventDTO.getMaxElo());
            newEvent.setTitle(eventDTO.getTitle());
            Sport sport = sportRepository.findSportByName(eventDTO.getSport());
            newEvent.setSport(sport);
            EventPlayer eventPlayer1 = eventPlayerRepository.findEventPlayerById(eventDTO.getPlayer1Id());
            EventPlayer eventPlayer2 = eventPlayerRepository.findEventPlayerById(eventDTO.getPlayer2Id());
            Set<EventPlayer> players = new HashSet<>();
            players.add(eventPlayer1);
            players.add(eventPlayer2);
            newEvent.setPlayers(players);
            eventRepository.save(newEvent);
        }
        return newEvent;
    }

    public boolean isValid(EventDTO eventDTO) {
        if (eventDTO.getDateStart() == null) {
            return false;
        }
        if (eventDTO.getDateEnd() == null) {
            return false;
        }
        if (eventDTO.getLocation() == null) {
            return false;
        }
        if (eventDTO.getMinElo() == null) {
            return false;
        }
        if (eventDTO.getMaxElo() == null) {
            return false;
        }
        if (eventDTO.getTitle() == null) {
            return false;
        }
        if (sportRepository.findSportByName(eventDTO.getSport()) == null) {
            return false;
        }
        if (eventPlayerRepository.findEventPlayerById(eventDTO.getPlayer1Id()) == null ||
                eventPlayerRepository.findEventPlayerById(eventDTO.getPlayer2Id()) == null) {
            return false;
        }
        return true;
    }
}

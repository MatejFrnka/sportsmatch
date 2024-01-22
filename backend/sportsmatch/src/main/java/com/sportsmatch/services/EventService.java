package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.*;
import com.sportsmatch.repos.EventPlayerRepository;
import com.sportsmatch.repos.EventRepository;
import com.sportsmatch.repos.SportRepository;
import com.sportsmatch.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserRepository userRepository;

    @Autowired
    public EventService(EventRepository eventRepository,
                        SportRepository sportRepository,
                        EventPlayerRepository eventPlayerRepository,
                        UserRepository userRepository) {
        this.eventRepository = eventRepository;
        this.sportRepository = sportRepository;
        this.eventPlayerRepository = eventPlayerRepository;
        this.userRepository = userRepository;
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
            addSportToNewEvent(eventDTO, newEvent);
            addEventPlayersToNewEvent(eventDTO, newEvent);
            eventRepository.save(newEvent);
            return newEvent;
        }
       return null;
    }

    private void addEventPlayersToNewEvent(EventDTO eventDTO, Event newEvent) {
        EventPlayer eventPlayer1 = new EventPlayer();
        EventPlayer eventPlayer2 = new EventPlayer();
        eventPlayer1.setPlayer(userRepository.findUserById(eventDTO.getPlayer1Id()));
        eventPlayer2.setPlayer(userRepository.findUserById(eventDTO.getPlayer2Id()));
        Set<EventPlayer> players = new HashSet<>();
        players.add(eventPlayer1);
        players.add(eventPlayer2);
        newEvent.setPlayers(players);
        eventRepository.save(newEvent);
        eventPlayer1.setEvent(newEvent);
        eventPlayer2.setEvent(newEvent);
    }

    private void addSportToNewEvent(EventDTO eventDTO, Event newEvent) {
        Sport sport = sportRepository.findSportByName(eventDTO.getSport());
        newEvent.setSport(sport);
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
        if (eventPlayerRepository.findEventPlayerByPlayer(userRepository.findUserById(eventDTO.getPlayer1Id())) == null ||
            eventPlayerRepository.findEventPlayerByPlayer(userRepository.findUserById(eventDTO.getPlayer2Id())) == null)
            return false;
        return true;
    }
}

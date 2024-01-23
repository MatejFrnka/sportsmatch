package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
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
    private SportRepository sportRepository;
    private UserRepository userRepository;

    public Event getEventById(Long id) {
        return eventRepository.findEventById(id);
    }

    public List<EventDTO> getAllEvents() {
        List<Event> eventList = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();
        for (Event event : eventList) {
            eventDTOList.add(new EventDTO(event));
        }
        return eventDTOList;
    }

    public Event createEvent(EventDTO eventDTO) {
        Event newEvent = new Event();
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

    private void addEventPlayersToNewEvent(EventDTO eventDTO, Event newEvent) {
        Set<EventPlayer> players = new HashSet<>();
        if (eventDTO.getPlayer1Id() != null) {
            EventPlayer eventPlayer1 = createEventPlayer(eventDTO.getPlayer1Id(), newEvent);
            players.add(eventPlayer1);
        }
        if (eventDTO.getPlayer2Id() != null) {
            EventPlayer eventPlayer2 = createEventPlayer(eventDTO.getPlayer2Id(), newEvent);
            players.add(eventPlayer2);
        }
        newEvent.setPlayers(players);
    }

    private EventPlayer createEventPlayer(Long playerId, Event newEvent) {
        EventPlayer eventPlayer = new EventPlayer();
        eventPlayer.setPlayer(
            userRepository.findUserById(playerId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST)));
        eventPlayer.setEvent(newEvent);
        return eventPlayer;
    }

    private void addSportToNewEvent(EventDTO eventDTO, Event newEvent) {
        Sport sport = sportRepository.findSportByName(eventDTO.getSport())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.BAD_REQUEST));
        newEvent.setSport(sport);
    }

    public void deleteEventFromDatabase(Event eventById) {
        eventRepository.deleteById(eventById.getId());
    }
}

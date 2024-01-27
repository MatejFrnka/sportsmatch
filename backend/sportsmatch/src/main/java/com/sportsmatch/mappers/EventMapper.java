package com.sportsmatch.mappers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.Sport;
import com.sportsmatch.repos.SportRepository;
import com.sportsmatch.repos.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class EventMapper {

    private ModelMapper modelMapper;
    private UserRepository userRepository;
    private SportRepository sportRepository;

    @Autowired
    public EventMapper(ModelMapper modelMapper,
                       UserRepository userRepository,
                       SportRepository sportRepository){
        this.userRepository = userRepository;
        this.sportRepository = sportRepository;
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public EventDTO convertEventToEventDTO(Event event){
        EventDTO eventDTO = modelMapper.map(event, EventDTO.class);
        List<EventPlayer> eventPlayers = event.getPlayers().stream().toList();
        if (eventPlayers.size() > 0) {
            eventDTO.setPlayer1Id(eventPlayers.get(0).getPlayer().getId());
        }
        if (eventPlayers.size() > 1) {
            eventDTO.setPlayer2Id(eventPlayers.get(1).getPlayer().getId());
        }
        eventDTO.setSport(event.getSport().getName());
        return eventDTO;
    }

    public Event convertEventDTOtoEvent(EventDTO eventDTO){
        modelMapper.typeMap(EventDTO.class, Event.class)
                .addMappings(e -> e.skip(Event::setId));
        Event event = modelMapper.map(eventDTO, Event.class);
        addEventPlayersToNewEvent(eventDTO, event);
        addSportToNewEvent(eventDTO, event);
        return event;
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
}

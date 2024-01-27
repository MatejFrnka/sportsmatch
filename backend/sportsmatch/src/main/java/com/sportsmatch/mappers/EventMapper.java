package com.sportsmatch.mappers;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EventMapper {

    private ModelMapper modelMapper;

    @Autowired
    public EventMapper(ModelMapper modelMapper){
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
        return modelMapper.map(eventDTO, Event.class);
    }


}

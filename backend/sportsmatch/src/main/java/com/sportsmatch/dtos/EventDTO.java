package com.sportsmatch.dtos;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EventDTO {

    private Long id;

    private LocalDateTime dateStart;

    private LocalDateTime dateEnd;

    private String location;

    private Integer minElo;

    private Integer maxElo;

    private String title;

    private Long player1Id;
    private Long player2Id;

    private String sport;

    public EventDTO(Event event){
        List<EventPlayer> eventPlayers = event.getPlayers().stream().toList();
        this.id = event.getId();
        this.dateStart = event.getDateStart();
        this.dateEnd = event.getDateEnd();
        this.location = event.getLocation();
        this.minElo = event.getMinElo();
        this.maxElo = event.getMaxElo();
        this.title = event.getTitle();
        this.player1Id = eventPlayers.get(0).getPlayer().getId();
        this.player2Id = eventPlayers.get(1).getPlayer().getId();
        this.sport = event.getSport().getName();
    }
}

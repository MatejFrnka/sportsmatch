package com.sportsmatch.dtos;

import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull
    private LocalDateTime dateStart;
    @NotNull
    private LocalDateTime dateEnd;
    @NotBlank
    private String location;
    @NotNull
    private Integer minElo;
    @NotNull
    private Integer maxElo;
    @NotBlank
    private String title;

    private Long player1Id;
    private Long player2Id;
    @NotBlank
    private String sport;

    public EventDTO(Event event) {
        List<EventPlayer> eventPlayers = event.getPlayers().stream().toList();
        this.id = event.getId();
        this.dateStart = event.getDateStart();
        this.dateEnd = event.getDateEnd();
        this.location = event.getLocation();
        this.minElo = event.getMinElo();
        this.maxElo = event.getMaxElo();
        this.title = event.getTitle();
        if (eventPlayers.size() > 0) {
            this.player1Id = eventPlayers.get(0).getPlayer().getId();
        }
        if (eventPlayers.size() > 1) {
            this.player2Id = eventPlayers.get(1).getPlayer().getId();
        }
        this.sport = event.getSport().getName();
    }
}

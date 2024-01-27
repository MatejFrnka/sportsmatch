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

}

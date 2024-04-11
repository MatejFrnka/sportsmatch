package com.sportsmatch.services;

import com.sportsmatch.dtos.EventDTO;
import com.sportsmatch.dtos.EventHistoryDTO;
import com.sportsmatch.dtos.HostEventDTO;
import com.sportsmatch.dtos.RequestEventDTO;
import com.sportsmatch.mappers.EventMapper;
import com.sportsmatch.models.Event;
import com.sportsmatch.models.EventPlayer;
import com.sportsmatch.models.EventStatusOptions;
import com.sportsmatch.models.Place;
import com.sportsmatch.models.User;
import com.sportsmatch.repositories.EventPlayerRepository;
import com.sportsmatch.repositories.EventRepository;
import com.sportsmatch.repositories.PlaceRepository;
import com.sportsmatch.repositories.SportRepository;
import com.sportsmatch.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventService {
    private final UserService userService;
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final UserRepository userRepository;
    private final SportRepository sportRepository;
    private final EventPlayerRepository eventPlayerRepository;
    private final PlaceRepository placeRepository;


    public Event getEventById(Long id) {
        return eventRepository
            .findEventById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
    }

    public EventDTO getEventDTObyEventId(Long id) {
        Event event =
            eventRepository
                .findEventById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));
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

    public List<EventDTO> getEventsBySports(List<Long> sportsIds) {
        List<EventDTO> eventDTOList = new ArrayList<>();
        List<Event> eventListBySport = eventRepository.findAllBySportIdIn(sportsIds);
        for (Event event : eventListBySport) {
            eventDTOList.add(getEventDTObyEventId(event.getId()));
        }
        return eventDTOList;
    }

    public EventPlayer addPlayerToEvent(Long playerId, Long eventId) {
        EventPlayer eventPlayer = new EventPlayer();
        eventPlayer.setPlayer(
            userRepository
                .findUserById(playerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        eventPlayer.setEvent(
            eventRepository
                .findEventById(eventId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        return eventPlayerRepository.save(eventPlayer);
    }

    public Event createEvent(HostEventDTO hostEventDTO) {
        User user = userService.getUserFromContext();
        Event newEvent = eventMapper.convertHostEventDTOtoEvent(hostEventDTO);
        Place place = placeRepository.findById(hostEventDTO.getLocationId()).orElseThrow();
        newEvent.setPlace(place);
        newEvent.setSport(
            sportRepository
                .findSportByName(hostEventDTO.getSport())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST)));
        newEvent = eventRepository.save(newEvent);
        EventPlayer userPlayer = new EventPlayer(null, null, user, newEvent);
        eventPlayerRepository.save(userPlayer);

//    Set<EventPlayer> players = new HashSet<>();
//    if (hostEventDTO.getPlayer1Id() != null) {
//      players.add(addPlayerToEvent(hostEventDTO.getPlayer1Id(), newEvent.getId()));
//    }
//    if (hostEventDTO.getPlayer2Id() != null) {
//      players.add(addPlayerToEvent(hostEventDTO.getPlayer2Id(), newEvent.getId()));
//    }
//
//    newEvent.setPlayers(players);
        return newEvent;
    }

    public void deleteEventFromDatabase(Event eventById) {
        eventRepository.deleteById(eventById.getId());
    }

    /**
     * Retrieves the event history of the logged-in user.
     *
     * @param pageable contains the pagination information (page, size)
     * @return a list of EventHistoryDTOs representing the logged-in user's event history
     */
    public List<EventHistoryDTO> getEventsHistory(final Pageable pageable) {
        String loggedUserName = userService.getUserFromContext().getName();

        return eventRepository.findEventsByUser(loggedUserName, LocalDateTime.now(), pageable)
            .stream()
            .map(event -> eventMapper.toDTO(event, loggedUserName,
                checkScoreMatch(event.getPlayers())))
            .collect(Collectors.toList());
    }

    /**
     * Returns the checked status of the match (check the score is matching or missing).
     *
     * @param players who entered the event (2 playerEvent)
     * @return the status of the match
     * There is 4 option:
     * - Invalid Player -> if one of the player don't present.
     * - Waiting for ratings -> if one of the players doesn't response with the score information.
     * - Match -> when both player submitted their result and it is match.
     * - Mismatch -> when both players have submitted their result, and it isn't a match.
     */
    public EventStatusOptions checkScoreMatch(Set<EventPlayer> players) {

        User loggedUser = userService.getUserFromContext();

        EventPlayer loggedPlayer =
            players.stream()
                .filter(p -> p.getPlayer().getName().equals(loggedUser.getName()))
                .findFirst()
                .orElse(null);

        EventPlayer otherPlayer =
            players.stream()
                .filter(p -> !Objects.equals(p.getPlayer().getName(), loggedUser.getName()))
                .findFirst()
                .orElse(null);

        if (loggedPlayer == null || otherPlayer == null) {
            return EventStatusOptions.INVALID_PLAYER;
        } else if (loggedPlayer.getMyScore() == null
            || loggedPlayer.getOpponentScore() == null
            || otherPlayer.getMyScore() == null
            || otherPlayer.getOpponentScore() == null) {
            return EventStatusOptions.WAITING_FOR_RATING;
        }

        int loggedPlayerOwnScore = loggedPlayer.getMyScore();
        int loggedPlayerOpponentScore = loggedPlayer.getOpponentScore();
        int otherPlayerOwnScore = otherPlayer.getMyScore();
        int otherPlayerLoggedScore = otherPlayer.getOpponentScore();

        if (loggedPlayerOwnScore == otherPlayerLoggedScore
            && loggedPlayerOpponentScore == otherPlayerOwnScore) {
            return EventStatusOptions.MATCH;
        } else {
            return EventStatusOptions.MISMATCH;
        }
    }

    public void joinEvent(Long id) throws Exception {
        Event event = getEventById(id);
        User loggedUser = userService.getUserFromContext();
        Set<EventPlayer> eventPlayerSet = event.getPlayers();
        if (eventPlayerSet.size() <= 1
            && eventPlayerRepository.findEventPlayerByEventAndPlayer(event, loggedUser).isEmpty()) {
            EventPlayer eventPlayer = new EventPlayer();
            eventPlayer.setPlayer(loggedUser);
            eventPlayer.setEvent(event);
            eventPlayerRepository.save(eventPlayer);
        } else if (eventPlayerRepository
            .findEventPlayerByEventAndPlayer(event, loggedUser)
            .isPresent()) {
            throw new Exception("User " + loggedUser.getName() + " has already joined the event.");
        } else {
            throw new Exception("Event has already two players.");
        }
    }

    /**
     * Finds events near the provided location and filters them based on optional sport names filters, returning a page of event data transfer objects (DTOs).
     *
     * @param requestEventDTO containing the request parameters for filtering events.
     * @param pageable        containing page and size
     * @return a list of EventDTO representing Events entity and filtered by sport names is given, and coordinate.
     */
    public List<EventDTO> getNearbyEvents(RequestEventDTO requestEventDTO,
                                          final Pageable pageable) {

        // Convert the given sportNames to lowercase because of the native custom query
        List<String> sportNamesWithLowerCase =
            requestEventDTO.getSportsName().stream().map(String::toLowerCase).toList();

        List<Event> events = eventRepository.findNearbyEvents(requestEventDTO.getLongitude(),
            requestEventDTO.getLatitude(), sportNamesWithLowerCase, pageable);

        return events.stream().map(eventMapper::convertEventToEventDTO)
            .collect(Collectors.toList());
    }
}

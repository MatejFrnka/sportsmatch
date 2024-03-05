package com.sportsmatch;

import com.sportsmatch.models.*;
import com.sportsmatch.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
@EnableWebMvc
public class SportsmatchApplication implements CommandLineRunner {

  private final UserRepository userRepository;
  private final SportRepository sportRepository;
  private final SportUserRepository sportUserRepository;
  private final EventPlayerRepository eventPlayerRepository;
  private final EventRepository eventRepository;
  private final PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication.run(SportsmatchApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    addData();
  }

  public void addData() {
    List<Sport> sports = addSports();
    List<User> users = addUsers();
    List<SportUser> sportUsers = addSportUsers(sports, users);
    List<Event> events = addEvents(sports);
    List<EventPlayer> eventPlayers = addEventPlayers(events, users);
  }

  public List<EventPlayer> addEventPlayers(List<Event> events, List<User> users) {
    eventPlayerRepository.save(new EventPlayer(1, 3, users.get(0), events.get(0)));
    eventPlayerRepository.save(new EventPlayer(3, 1, users.get(9), events.get(0)));
    eventPlayerRepository.save(new EventPlayer(3, 2, users.get(1), events.get(1)));
    eventPlayerRepository.save(new EventPlayer(3, 1, users.get(8), events.get(1)));
    eventPlayerRepository.save(new EventPlayer(2, 2, users.get(2), events.get(2)));
    eventPlayerRepository.save(new EventPlayer(2, 2, users.get(7), events.get(2)));
    eventPlayerRepository.save(new EventPlayer(0, 1, users.get(3), events.get(3)));
    eventPlayerRepository.save(new EventPlayer(1, 0, users.get(6), events.get(3)));
    eventPlayerRepository.save(new EventPlayer(2, 1, users.get(4), events.get(4)));
    eventPlayerRepository.save(new EventPlayer(1, 2, users.get(5), events.get(4)));
    return eventPlayerRepository.findAll();
  }

  public List<Event> addEvents(List<Sport> sports) {
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 5, 1, 14, 30),
            LocalDateTime.of(2024, 5, 1, 16, 30),
            "Prague, Stadium A",
            1200,
            2000,
            "Badminton match",
            sports.get(0)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 7, 10, 18, 0),
            LocalDateTime.of(2024, 7, 10, 20, 0),
            "Brno, Gym",
            1500,
            2500,
            "Boxing event",
            sports.get(1)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 8, 5, 9, 0),
            LocalDateTime.of(2024, 8, 5, 11, 0),
            "Prague",
            800,
            1600,
            "Table Tennis for beginners",
            sports.get(2)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 9, 20, 15, 0),
            LocalDateTime.of(2024, 9, 20, 17, 0),
            "Prague, Squash Centre",
            1400,
            2200,
            "Squash challenge",
            sports.get(3)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 6, 15, 10, 0),
            LocalDateTime.of(2024, 6, 15, 12, 0),
            "Prague Stvanice",
            1000,
            1800,
            "Tennis Open",
            sports.get(4)));
    return eventRepository.findAll();
  }

  public List<SportUser> addSportUsers(List<Sport> sports, List<User> users) {
    sportUserRepository.save(new SportUser(users.get(0), sports.get(0)));
    sportUserRepository.save(new SportUser(users.get(9), sports.get(0)));
    sportUserRepository.save(new SportUser(users.get(1), sports.get(1)));
    sportUserRepository.save(new SportUser(users.get(8), sports.get(1)));
    sportUserRepository.save(new SportUser(users.get(2), sports.get(2)));
    sportUserRepository.save(new SportUser(users.get(7), sports.get(2)));
    sportUserRepository.save(new SportUser(users.get( dev3), sports.get(3)));
    sportUserRepository.save(new SportUser(users.get(6), sports.get(3)));
    sportUserRepository.save(new SportUser(users.get(4), sports.get(4)));
    sportUserRepository.save(new SportUser(users.get(5), sports.get(4)));
    return sportUserRepository.findAll();
  }

  public List<User> addUsers() {
    userRepository.save(
        new User(
            "john.doe@example.com",
            passwordEncoder.encode("pass123"),
            "johndoe87",
            Gender.MALE,
            LocalDate.of(1990, 5, 15)));
    userRepository.save(
        new User(
            "alice.smith@example.com",
            passwordEncoder.encode("securePass"),
            "alice.smith",
            Gender.FEMALE,
            LocalDate.of(1985, 11, 22)));
    userRepository.save(
        new User(
            "bob.jones@example.com",
            passwordEncoder.encode("b0bPass"),
            "bobjones22",
            Gender.MALE,
            LocalDate.of(1992, 8, 10)));
    userRepository.save(
        new User(
            "emily.white@example.com",
            passwordEncoder.encode("emilyPass"),
            "em_white",
            Gender.FEMALE,
            LocalDate.of(1988, 4, 3)));
    userRepository.save(
        new User(
            "mike.jackson@example.com",
            passwordEncoder.encode("mjPass2020"),
            "mikej",
            Gender.MALE,
            LocalDate.of(1995, 12, 18)));
    userRepository.save(
        new User(
            "lisa.martin@example.com",
            passwordEncoder.encode("lisaPass123"),
            "lisa_m",
            Gender.FEMALE,
            LocalDate.of(1998, 7, 25)));
    userRepository.save(
        new User(
            "chris.brown@example.com",
            passwordEncoder.encode("cbrownPass"),
            "chris_b",
            Gender.MALE,
            LocalDate.of(1983, 9, 14)));
    userRepository.save(
        new User(
            "sarah.green@example.com",
            passwordEncoder.encode("greenSarah"),
            "s_green",
            Gender.FEMALE,
            LocalDate.of(1993, 2, 9)));
    userRepository.save(
        new User(
            "ryan.miller@example.com",
            passwordEncoder.encode("ryanPass456"),
            "ryanm",
            Gender.MALE,
            LocalDate.of(1987, 6, 30)));
    userRepository.save(
        new User(
            "jessica.ward@example.com",
            passwordEncoder.encode("jessWard789"),
            "jess_ward",
            Gender.FEMALE,
            LocalDate.of(1991, 3, 12)));

    return userRepository.findAll();
  }

  public List<Sport> addSports() {
    String badmintonEmoji = "\uD83C\uDFF8"; //badminton racket emoji
    String boxingEmoji = "\uD83E\uDD4A"; //boxing glove emoji
    String tableTennisEmoji = "\uD83C\uDFD3"; //table tennis racket emoji
    String tennisEmoji = "\uD83C\uDFBE"; //tennis racket emoji
    String squashEmoji = "\uD83E\uDD4E"; //green softball emoji

    sportRepository.save(new Sport("Badminton", badmintonEmoji,"./assets/sport-component-badminton.png"));
    sportRepository.save(new Sport("Boxing", boxingEmoji, "./assets/sport-component-boxing.png"));
    sportRepository.save(new Sport("Table Tennis", tableTennisEmoji, "./assets/sport-component-table-tennis.png"));
    sportRepository.save(new Sport("Squash", squashEmoji, "./assets/sport-component-squash.png"));
    sportRepository.save(new Sport("Tennis", tennisEmoji, "./assets/sport-component-tennis.png"));

    return sportRepository.findAll();
  }
}

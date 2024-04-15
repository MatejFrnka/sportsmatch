package com.sportsmatch;

import com.sportsmatch.configs.InitProperties;
import com.sportsmatch.models.*;
import com.sportsmatch.repositories.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@AllArgsConstructor
@SpringBootApplication
@EnableWebMvc
@ConfigurationPropertiesScan("com.sportsmatch.configs")
public class SportsmatchApplication implements CommandLineRunner {

  private final UserRepository userRepository;
  private final SportRepository sportRepository;
  private final PlaceRepository placeRepository;
  private final SportUserRepository sportUserRepository;
  private final EventPlayerRepository eventPlayerRepository;
  private final EventRepository eventRepository;
  private final PasswordEncoder passwordEncoder;
  private final InitProperties initProperties;
  private final RatingRepository ratingRepository;
  private final UserEventRatingRepository userEventRatingRepository;

  public static void main(String[] args) {
    SpringApplication.run(SportsmatchApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    if (initProperties.getDatabaseInit()) {
      addData();
    }
  }

  public void addData() {
    List<Place> places = addPlaces();
    List<Sport> sports = addSports();
    List<User> users = addUsers();
    List<SportUser> sportUsers = addSportUsers(sports, users);
    List<Event> events = addEvents(sports, places);
    List<EventPlayer> eventPlayers = addEventPlayers(events, users);
    List<Rating> ratings = addRatings();
    addUserEventRating(ratings, users, events);
  }

  public void addUserEventRating(List<Rating> ratings, List<User> users, List<Event> events) {
    UserEventRating uer1 = new UserEventRating();
    uer1.setEvent(events.get(0));
    uer1.setPlayer(users.get(9));
    uer1.setOpponent(users.get(0));
    uer1.setUserRating(ratings.get(0));
    UserEventRating uer2 = new UserEventRating();
    uer2.setEvent(events.get(5));
    uer2.setPlayer(users.get(9));
    uer2.setOpponent(users.get(0));
    uer2.setUserRating(ratings.get(1));
    UserEventRating uer3 = new UserEventRating();
    uer3.setEvent(events.get(6));
    uer3.setPlayer(users.get(9));
    uer3.setOpponent(users.get(0));
    uer3.setUserRating(ratings.get(2));
    UserEventRating uer4 = new UserEventRating();
    uer4.setEvent(events.get(7));
    uer4.setPlayer(users.get(0));
    uer4.setOpponent(users.get(9));
    uer4.setUserRating(ratings.get(3));
    UserEventRating uer5 = new UserEventRating();
    uer5.setEvent(events.get(0));
    uer5.setPlayer(users.get(0));
    uer5.setOpponent(users.get(9));
    uer5.setUserRating(ratings.get(4));
    UserEventRating uer6 = new UserEventRating();
    uer6.setEvent(events.get(5));
    uer6.setPlayer(users.get(0));
    uer6.setOpponent(users.get(9));
    uer6.setUserRating(ratings.get(5));
    UserEventRating uer7 = new UserEventRating();
    uer7.setEvent(events.get(6));
    uer7.setPlayer(users.get(0));
    uer7.setOpponent(users.get(9));
    uer7.setUserRating(ratings.get(6));
    UserEventRating uer8 = new UserEventRating();
    uer8.setEvent(events.get(7));
    uer8.setPlayer(users.get(9));
    uer8.setOpponent(users.get(0));
    uer8.setUserRating(ratings.get(7));
    userEventRatingRepository.save(uer1);
    userEventRatingRepository.save(uer2);
    userEventRatingRepository.save(uer3);
    userEventRatingRepository.save(uer4);
    userEventRatingRepository.save(uer5);
    userEventRatingRepository.save(uer6);
    userEventRatingRepository.save(uer7);
    userEventRatingRepository.save(uer8);

    for (int i = 8; i < 58; i++) {
      UserEventRating uer =
          UserEventRating.builder()
              .event(events.get(7))
              .player(users.get(9))
              .opponent(users.get(0))
              .userRating(ratings.get(i))
              .build();
      userEventRatingRepository.save(uer);
    }
  }

  public List<Rating> addRatings() {
    Rating rating1 = new Rating();
    rating1.setStarRating(5);
    rating1.setTextRating("great");
    ratingRepository.save(rating1);
    Rating rating2 = new Rating();
    rating2.setStarRating(4);
    rating2.setTextRating("wonderful");
    ratingRepository.save(rating2);
    Rating rating3 = new Rating();
    rating3.setStarRating(2);
    rating3.setTextRating("could have been better");
    ratingRepository.save(rating3);
    Rating rating4 = new Rating();
    rating4.setStarRating(2);
    rating4.setTextRating("not good");
    ratingRepository.save(rating4);
    Rating rating5 = new Rating();
    rating5.setStarRating(4);
    rating5.setTextRating("perfect");
    ratingRepository.save(rating5);
    Rating rating6 = new Rating();
    rating6.setStarRating(4);
    rating6.setTextRating("good");
    ratingRepository.save(rating6);
    Rating rating7 = new Rating();
    rating7.setStarRating(1);
    rating7.setTextRating("bad");
    ratingRepository.save(rating7);
    Rating rating8 = new Rating();
    rating8.setStarRating(2);
    rating8.setTextRating("not so good");
    ratingRepository.save(rating8);

    Random rand = new Random();
    for (int i = 0; i < 50; i++) {
      int randomNumber = rand.nextInt(5 - 1 + 1) + 1;
      Rating rating =
          Rating.builder().starRating(randomNumber).textRating("test rating " + i).build();
      ratingRepository.save(rating);
    }
    return ratingRepository.findAll();
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
    eventPlayerRepository.save(new EventPlayer(1, 3, users.get(0), events.get(5)));
    eventPlayerRepository.save(new EventPlayer(3, 1, users.get(9), events.get(5)));
    eventPlayerRepository.save(new EventPlayer(1, 3, users.get(0), events.get(6)));
    eventPlayerRepository.save(new EventPlayer(3, 1, users.get(9), events.get(6)));
    eventPlayerRepository.save(new EventPlayer(1, 3, users.get(0), events.get(7)));
    eventPlayerRepository.save(new EventPlayer(3, 1, users.get(9), events.get(7)));
    return eventPlayerRepository.findAll();
  }

  public List<Event> addEvents(List<Sport> sports, List<Place> places) {
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 5, 1, 14, 30),
            LocalDateTime.of(2024, 5, 1, 16, 30),
            "Prague, Stadium A",
            1200,
            2000,
            "Badminton match",
            sports.get(0),
            places.get(0)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 7, 10, 18, 0),
            LocalDateTime.of(2024, 7, 10, 20, 0),
            "Brno, Gym",
            1500,
            2500,
            "Boxing event",
            sports.get(1),
            places.get(1)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 8, 5, 9, 0),
            LocalDateTime.of(2024, 8, 5, 11, 0),
            "Prague",
            800,
            1600,
            "Table Tennis for beginners",
            sports.get(2),
            places.get(2)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 9, 20, 15, 0),
            LocalDateTime.of(2024, 9, 20, 17, 0),
            "Prague, Squash Centre",
            1400,
            2200,
            "Squash challenge",
            sports.get(3),
            places.get(3)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2024, 6, 15, 10, 0),
            LocalDateTime.of(2024, 6, 15, 12, 0),
            "Prague Stvanice",
            1000,
            1800,
            "Tennis Open 1",
            sports.get(4),
            places.get(4)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2023, 6, 15, 10, 0),
            LocalDateTime.of(2023, 6, 15, 12, 0),
            "Prague Stvanice",
            1000,
            1800,
            "Tennis Open 2",
            sports.get(4),
            places.get(4)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2023, 6, 15, 10, 0),
            LocalDateTime.of(2023, 6, 15, 12, 0),
            "Prague Stvanice",
            1000,
            1800,
            "Tennis Open 3",
            sports.get(4),
            places.get(4)));
    eventRepository.save(
        new Event(
            LocalDateTime.of(2023, 6, 15, 10, 0),
            LocalDateTime.of(2023, 6, 15, 12, 0),
            "Prague Stvanice",
            1000,
            1800,
            "Tennis Open 4",
            sports.get(4),
            places.get(4)));
    return eventRepository.findAll();
  }

  public List<SportUser> addSportUsers(List<Sport> sports, List<User> users) {
    sportUserRepository.save(new SportUser(users.get(0), sports.get(0)));
    sportUserRepository.save(new SportUser(users.get(9), sports.get(0)));
    sportUserRepository.save(new SportUser(users.get(1), sports.get(1)));
    sportUserRepository.save(new SportUser(users.get(8), sports.get(1)));
    sportUserRepository.save(new SportUser(users.get(2), sports.get(2)));
    sportUserRepository.save(new SportUser(users.get(7), sports.get(2)));
    // sportUserRepository.save(new SportUser(users.get( dev3), sports.get(3)));
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
    String badmintonEmoji = "\uD83C\uDFF8"; // badminton racket emoji
    String boxingEmoji = "\uD83E\uDD4A"; // boxing glove emoji
    String tableTennisEmoji = "\uD83C\uDFD3"; // table tennis racket emoji
    String tennisEmoji = "\uD83C\uDFBE"; // tennis racket emoji
    String squashEmoji = "\uD83E\uDD4E"; // green softball emoji

    sportRepository.save(
        new Sport("Badminton", badmintonEmoji, "./assets/sport-component-badminton.png"));
    sportRepository.save(new Sport("Boxing", boxingEmoji, "./assets/sport-component-boxing.png"));
    sportRepository.save(
        new Sport("Table Tennis", tableTennisEmoji, "./assets/sport-component-table-tennis.png"));
    sportRepository.save(new Sport("Squash", squashEmoji, "./assets/sport-component-squash.png"));
    sportRepository.save(new Sport("Tennis", tennisEmoji, "./assets/sport-component-tennis.png"));

    return sportRepository.findAll();
  }

  public List<Place> addPlaces() {
    placeRepository.save(new Place("Kanizsa Aréna", "Hungary", 46.4618, 17.0102));
    placeRepository.save(new Place("AT&T Stadium Box", "USA", 32.7686, -96.711308));
    placeRepository.save(new Place("Parc des Princes", "France", 48.8413634, 2.2530693));
    placeRepository.save(new Place("San Siro", "Italy", 45.480290, 9.123080));
    placeRepository.save(new Place("Burj Al Arab Helipad Tennis", "Dubai", 25.1411934, 55.1855516));
    placeRepository.save(new Place("Old Trafford", "England", 53.464560, -2.289720));

    return placeRepository.findAll();
  }
}

package com.sportsmatch;

<<<<<<< HEAD
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
=======
import com.sportsmatch.models.*;
import com.sportsmatch.repos.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
>>>>>>> 7b413cd2aae6abef434578b98f285f6cfaa85a8a
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@SpringBootApplication
<<<<<<< HEAD
@OpenAPIDefinition
@EnableWebMvc
public class SportsmatchApplication {
=======
public class SportsmatchApplication implements CommandLineRunner {

    private final UserRepository userRepo;
    private final SportRepository sportRepo;
    private final SportUserRepository sportUserRepo;
    private final EventPlayerRepository eventPlayerRepo;
    private final EventRepository eventRepo;
>>>>>>> 7b413cd2aae6abef434578b98f285f6cfaa85a8a

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
        eventPlayerRepo.save(new EventPlayer(1, 3, users.get(0), events.get(0)));
        eventPlayerRepo.save(new EventPlayer(3, 1, users.get(9), events.get(0)));
        eventPlayerRepo.save(new EventPlayer(3, 2, users.get(1), events.get(1)));
        eventPlayerRepo.save(new EventPlayer(3, 1, users.get(8), events.get(1)));
        eventPlayerRepo.save(new EventPlayer(2, 2, users.get(2), events.get(2)));
        eventPlayerRepo.save(new EventPlayer(2, 2, users.get(7), events.get(2)));
        eventPlayerRepo.save(new EventPlayer(0, 1, users.get(3), events.get(3)));
        eventPlayerRepo.save(new EventPlayer(1, 0, users.get(6), events.get(3)));
        eventPlayerRepo.save(new EventPlayer(2, 1, users.get(4), events.get(4)));
        eventPlayerRepo.save(new EventPlayer(1, 2, users.get(5), events.get(4)));
        return eventPlayerRepo.findAll();
    }

    public List<Event> addEvents(List<Sport> sports) {
        eventRepo.save(new Event(
                LocalDateTime.of(2024, 5, 1, 14, 30),
                LocalDateTime.of(2024, 5, 1, 16, 30),
                "Prague, Stadium A", 1200, 2000,
                "Badminton match", sports.get(0)));
        eventRepo.save(new Event(
                LocalDateTime.of(2024, 7, 10, 18, 0),
                LocalDateTime.of(2024, 7, 10, 20, 0),
                "Brno, Gym", 1500, 2500,
                "Boxing event", sports.get(1)));
        eventRepo.save(new Event(
                LocalDateTime.of(2024, 8, 5, 9, 0),
                LocalDateTime.of(2024, 8, 5, 11, 0),
                "Prague", 800, 1600,
                "Table Tennis for beginners", sports.get(2)));
        eventRepo.save(new Event(
                LocalDateTime.of(2024, 9, 20, 15, 0),
                LocalDateTime.of(2024, 9, 20, 17, 0),
                "Prague, Squash Centre", 1400, 2200,
                "Squash challenge", sports.get(3)));
        eventRepo.save(new Event(
                LocalDateTime.of(2024, 6, 15, 10, 0),
                LocalDateTime.of(2024, 6, 15, 12, 0),
                "Prague Stvanice", 1000, 1800,
                "Tennis Open", sports.get(4)));
        return eventRepo.findAll();
    }

    public List<SportUser> addSportUsers(List<Sport> sports, List<User> users) {
        sportUserRepo.save(new SportUser(users.get(0), sports.get(0)));
        sportUserRepo.save(new SportUser(users.get(9), sports.get(0)));
        sportUserRepo.save(new SportUser(users.get(1), sports.get(1)));
        sportUserRepo.save(new SportUser(users.get(8), sports.get(1)));
        sportUserRepo.save(new SportUser(users.get(2), sports.get(2)));
        sportUserRepo.save(new SportUser(users.get(7), sports.get(2)));
        sportUserRepo.save(new SportUser(users.get(3), sports.get(3)));
        sportUserRepo.save(new SportUser(users.get(6), sports.get(3)));
        sportUserRepo.save(new SportUser(users.get(4), sports.get(4)));
        sportUserRepo.save(new SportUser(users.get(5), sports.get(4)));
        return sportUserRepo.findAll();
    }

    public List<User> addUsers() {
        userRepo.save(new User("john.doe@example.com",
                "pass123", "johndoe87",
                Gender.MALE, LocalDate.of(1990, 5, 15)));
        userRepo.save(new User("alice.smith@example.com",
                "securePass", "alice.smith",
                Gender.FEMALE, LocalDate.of(1985, 11, 22)));
        userRepo.save(new User("bob.jones@example.com",
                "b0bPass", "bobjones22",
                Gender.MALE, LocalDate.of(1992, 8, 10)));
        userRepo.save(new User("emily.white@example.com",
                "emilyPass", "em_white",
                Gender.FEMALE, LocalDate.of(1988, 4, 3)));
        userRepo.save(new User("mike.jackson@example.com",
                "mjPass2020", "mikej",
                Gender.MALE, LocalDate.of(1995, 12, 18)));
        userRepo.save(new User("lisa.martin@example.com",
                "lisaPass123", "lisa_m",
                Gender.FEMALE, LocalDate.of(1998, 7, 25)));
        userRepo.save(new User("chris.brown@example.com",
                "cbrownPass", "chris_b",
                Gender.MALE, LocalDate.of(1983, 9, 14)));
        userRepo.save(new User("sarah.green@example.com",
                "greenSarah", "s_green",
                Gender.FEMALE, LocalDate.of(1993, 2, 9)));
        userRepo.save(new User("ryan.miller@example.com",
                "ryanPass456", "ryanm",
                Gender.MALE, LocalDate.of(1987, 6, 30)));
        userRepo.save(new User("jessica.ward@example.com",
                "jessWard789", "jess_ward",
                Gender.FEMALE, LocalDate.of(1991, 3, 12)));

        return userRepo.findAll();
    }

    public List<Sport> addSports() {
        sportRepo.save(new Sport("Badminton"));
        sportRepo.save(new Sport("Boxing"));
        sportRepo.save(new Sport("Table Tennis"));
        sportRepo.save(new Sport("Squash"));
        sportRepo.save(new Sport("Tennis"));

        return sportRepo.findAll();
    }
}

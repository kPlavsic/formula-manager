package com.formula.manager;

import com.formula.manager.model.*;
import com.formula.manager.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

/**
 * Interactive console menu for Formula Manager application.
 * Implements CommandLineRunner to start automatically on application launch.
 *
 * @author kPlavsic
 * @version 1.0
 */
//@Component
public class ConsoleMenu implements CommandLineRunner {

    private Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * Service for championship operations.
     */
    @Autowired
    private ChampionshipService championshipService;

    /**
     * Service for season operations.
     */
    @Autowired
    private SeasonService seasonService;

    /**
     * Service for circuit operations.
     */
    @Autowired
    private CircuitService circuitService;

    /**
     * Service for race operations.
     */
    @Autowired
    private RaceService raceService;

    /**
     * Service for driver operations.
     */
    @Autowired
    private DriverService driverService;

    /**
     * Service for team operations.
     */
    @Autowired
    private TeamService teamService;

    /**
     * Service for car operations.
     */
    @Autowired
    private CarService carService;

    /**
     * Service for JSON operations.
     */
    @Autowired
    private JsonService jsonService;

    /**
     * Clears the console screen.
     */
    private void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Runs the main console menu loop.
     *
     * @param args command line arguments
     */
    @Override
    public void run(String... args) {
        System.out.println("========================================");
        System.out.println("     FORMULA MANAGER v1.0              ");
        System.out.println("========================================");

        boolean running = true;
        while (running) {
            clearConsole();
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Championship Management");
            System.out.println("2. Season Management");
            System.out.println("3. Circuit Management");
            System.out.println("4. Team Management");
            System.out.println("5. Car Management");
            System.out.println("6. Driver Management");
            System.out.println("7. Race Management");
            System.out.println("8. JSON Operations");
            System.out.println("0. Exit");
            System.out.print("Select option: ");

            int choice = readInt();
            switch (choice) {
                case 1 -> championshipMenu();
                case 2 -> seasonMenu();
                case 3 -> circuitMenu();
                case 4 -> teamMenu();
                case 5 -> carMenu();
                case 6 -> driverMenu();
                case 7 -> raceMenu();
                case 8 -> jsonMenu();
                case 0 -> {
                    System.out.println("Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option, try again.");
            }
        }
        scanner.close();
    }

    /**
     * Reads an integer from console input safely.
     *
     * @return the integer entered by the user
     */
    private int readInt() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Reads a double from console input safely.
     *
     * @return the double entered by the user
     */
    private double readDouble() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /**
     * Reads a LocalDate from console input safely.
     *
     * @return the LocalDate entered by the user, or null if invalid
     */
    private LocalDate readDate() {
        try {
            return LocalDate.parse(scanner.nextLine().trim(), DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date format! Use dd/MM/yyyy");
            return null;
        }
    }


    /**
     * Displays and handles the Championship management menu.
     */
    private void championshipMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- CHAMPIONSHIP MANAGEMENT ---");
            System.out.println("1. Add Championship");
            System.out.println("2. View All Championships");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Championship --");
                    System.out.print("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Type: ");
                    String type = scanner.nextLine().trim();
                    System.out.print("Year: ");
                    int year = readInt();
                    System.out.print("Prize Money: ");
                    double prizeMoney = readDouble();
                    System.out.print("Organizer: ");
                    String organizer = scanner.nextLine().trim();

                    try {
                        Championship c = new Championship(null, name, type,
                                year, prizeMoney, organizer, null);
                        championshipService.addChampionship(c);
                        System.out.println("✓ Championship added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- All Championships --");
                    List<Championship> championships =
                            championshipService.getAllChampionships();
                    if (championships.isEmpty()) {
                        System.out.println("No championships found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        championships.forEach(c -> System.out.println(
                                "  [" + c.getId() + "] " + c.getName() +
                                        " | " + c.getType() +
                                        " | " + c.getYear() +
                                        " | Organizer: " + c.getOrganizer()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the Season management menu.
     */
    private void seasonMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- SEASON MANAGEMENT ---");
            System.out.println("1. Add Season");
            System.out.println("2. View Active Seasons");
            System.out.println("3. View Seasons by Championship");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Season --");
                    System.out.println("Available Championships:");
                    List<Championship> championships =
                            championshipService.getAllChampionships();
                    if (championships.isEmpty()) {
                        System.out.println("No championships found. Add a championship first!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    championships.forEach(c -> System.out.println(
                            "  [" + c.getId() + "] " + c.getName()));
                    System.out.print("Select Championship ID: ");
                    long championshipId = readInt();
                    Championship championship = championships.stream()
                            .filter(c -> c.getId() == championshipId)
                            .findFirst().orElse(null);
                    if (championship == null) {
                        System.out.println("✗ Championship not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;

                    }
                    System.out.print("Year: ");
                    int year = readInt();
                    System.out.print("Number of Races: ");
                    int numberOfRaces = readInt();
                    System.out.print("Active (true/false): ");
                    boolean active = Boolean.parseBoolean(scanner.nextLine().trim());
                    System.out.print("Start Date (dd/MM/yyyy): ");
                    LocalDate startDate = readDate();
                    if (startDate == null) break;

                    try {
                        Season s = new Season(null, year, numberOfRaces,
                                active, startDate, championship, null);
                        seasonService.addSeason(s);
                        System.out.println("✓ Season added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- Active Seasons --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        seasons.forEach(s -> System.out.println(
                                "  [" + s.getId() + "] " + s.getYear() +
                                        " | Races: " + s.getNumberOfRaces() +
                                        " | Championship: " + s.getChampionship().getName()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Seasons by Championship --");
                    List<Championship> championships =
                            championshipService.getAllChampionships();
                    if (championships.isEmpty()) {
                        System.out.println("No championships found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    championships.forEach(c -> System.out.println(
                            "  [" + c.getId() + "] " + c.getName()));
                    System.out.print("Select Championship ID: ");
                    long championshipId = readInt();
                    Championship championship = championships.stream()
                            .filter(c -> c.getId() == championshipId)
                            .findFirst().orElse(null);
                    if (championship == null) {
                        System.out.println("✗ Championship not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Season> seasons =
                            seasonService.getSeasonsByChampionship(championship);
                    if (seasons.isEmpty()) {
                        System.out.println("No seasons found for this championship.");
                    } else {
                        seasons.forEach(s -> System.out.println(
                                "  [" + s.getId() + "] " + s.getYear() +
                                        " | Races: " + s.getNumberOfRaces()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> {
                    System.out.println("Invalid option, try again.");
                    System.out.print("\nPress ENTER to continue...");
                    scanner.nextLine();
                }

            }
        }
    }

    /**
     * Displays and handles the Circuit management menu.
     */
    private void circuitMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- CIRCUIT MANAGEMENT ---");
            System.out.println("1. Add Circuit");
            System.out.println("2. View All Circuits");
            System.out.println("3. View Circuits by Country");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Circuit --");
                    System.out.print("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Country: ");
                    String country = scanner.nextLine().trim();
                    System.out.print("Length (km): ");
                    double length = readDouble();
                    System.out.print("Number of Turns: ");
                    int numberOfTurns = readInt();
                    System.out.print("Capacity: ");
                    int capacity = readInt();

                    try {
                        Circuit c = new Circuit(null, name, country,
                                length, numberOfTurns, capacity);
                        circuitService.addCircuit(c);
                        System.out.println("✓ Circuit added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- All Circuits --");
                    List<Circuit> circuits = circuitService.getAllCircuits();
                    if (circuits.isEmpty()) {
                        System.out.println("No circuits found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        circuits.forEach(c -> System.out.println(
                                "  [" + c.getId() + "] " + c.getName() +
                                        " | " + c.getCountry() +
                                        " | Length: " + c.getLength() + "km" +
                                        " | Turns: " + c.getNumberOfTurns() +
                                        " | Capacity: " + c.getCapacity()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Circuits by Country --");
                    System.out.print("Country: ");
                    String country = scanner.nextLine().trim();
                    List<Circuit> circuits =
                            circuitService.getCircuitsByCountry(country);
                    if (circuits.isEmpty()) {
                        System.out.println("No circuits found for: " + country);
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        circuits.forEach(c -> System.out.println(
                                "  [" + c.getId() + "] " + c.getName() +
                                        " | Length: " + c.getLength() + "km"));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the Team management menu.
     */
    private void teamMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- TEAM MANAGEMENT ---");
            System.out.println("1. Add Team");
            System.out.println("2. View All Teams");
            System.out.println("3. View Team Standings");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Team --");
                    System.out.print("Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Country: ");
                    String country = scanner.nextLine().trim();
                    System.out.print("Budget: ");
                    double budget = readDouble();
                    System.out.print("Team Principal: ");
                    String teamPrincipal = scanner.nextLine().trim();
                    System.out.print("Year of Forming: ");
                    int yearOfForming = readInt();

                    try {
                        Team t = new Team(null, name, country, budget,
                                teamPrincipal, yearOfForming, null, null);
                        teamService.addTeam(t);
                        System.out.println("✓ Team added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- All Teams --");
                    List<Team> teams = teamService.getAllTeams();
                    if (teams.isEmpty()) {
                        System.out.println("No teams found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        teams.forEach(t -> System.out.println(
                                "  [" + t.getId() + "] " + t.getName() +
                                        " | " + t.getCountry() +
                                        " | Principal: " + t.getTeamPrincipal() +
                                        " | Founded: " + t.getYearOfForming()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Team Standings --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear() +
                                    " | Championship: " + s.getChampionship().getName()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Team> standings = teamService.getTeamStandings(season);
                    if (standings.isEmpty()) {
                        System.out.println("No teams found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        System.out.println("\nTeam Standings for " + season.getYear() + ":");
                        standings.forEach(t -> {
                            int totalPoints = t.getDrivers() == null ? 0 :
                                    t.getDrivers().stream()
                                            .mapToInt(Driver::getPoints).sum();
                            System.out.println("  " + t.getName() +
                                    " - " + totalPoints + " pts");
                        });
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the Car management menu.
     */
    private void carMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- CAR MANAGEMENT ---");
            System.out.println("1. Add Car");
            System.out.println("2. View All Cars");
            System.out.println("3. View Cars by Team");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Car --");
                    List<Team> teams = teamService.getAllTeams();
                    if (teams.isEmpty()) {
                        System.out.println("No teams found. Add a team first!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    teams.forEach(t -> System.out.println(
                            "  [" + t.getId() + "] " + t.getName()));
                    System.out.print("Select Team ID: ");
                    long teamId = readInt();
                    Team team = teams.stream()
                            .filter(t -> t.getId() == teamId)
                            .findFirst().orElse(null);
                    if (team == null) {
                        System.out.println("✗ Team not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Model: ");
                    String model = scanner.nextLine().trim();
                    System.out.print("Motor: ");
                    String motor = scanner.nextLine().trim();
                    System.out.print("Year: ");
                    int year = readInt();
                    System.out.print("Horsepower: ");
                    int horsePower = readInt();
                    System.out.print("Weight (kg): ");
                    double weight = readDouble();

                    try {
                        Car c = new Car(null, model, motor, year,
                                horsePower, weight, team);
                        carService.addCar(c);
                        System.out.println("✓ Car added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- All Cars --");
                    List<Car> cars = carService.getAllCars();
                    if (cars.isEmpty()) {
                        System.out.println("No cars found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        cars.forEach(c -> System.out.println(
                                "  [" + c.getId() + "] " + c.getModel() +
                                        " | Motor: " + c.getMotor() +
                                        " | Year: " + c.getYear() +
                                        " | HP: " + c.getHorsePower() +
                                        " | Weight: " + c.getWeight() + "kg" +
                                        " | Team: " + c.getTeam().getName()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Cars by Team --");
                    List<Team> teams = teamService.getAllTeams();
                    if (teams.isEmpty()) {
                        System.out.println("No teams found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    teams.forEach(t -> System.out.println(
                            "  [" + t.getId() + "] " + t.getName()));
                    System.out.print("Select Team ID: ");
                    long teamId = readInt();
                    Team team = teams.stream()
                            .filter(t -> t.getId() == teamId)
                            .findFirst().orElse(null);
                    if (team == null) {
                        System.out.println("✗ Team not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Car> cars = carService.getCarsByTeam(team);
                    if (cars.isEmpty()) {
                        System.out.println("No cars found for: " + team.getName());
                    } else {
                        cars.forEach(c -> System.out.println(
                                "  [" + c.getId() + "] " + c.getModel() +
                                        " | Motor: " + c.getMotor() +
                                        " | HP: " + c.getHorsePower()));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the Driver management menu.
     */
    private void driverMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- DRIVER MANAGEMENT ---");
            System.out.println("1. Add Driver");
            System.out.println("2. Update Driver");
            System.out.println("3. Delete Driver");
            System.out.println("4. Driver Standings");
            System.out.println("5. Driver History");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Driver --");
                    List<Team> teams = teamService.getAllTeams();
                    if (teams.isEmpty()) {
                        System.out.println("No teams found. Add a team first!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    teams.forEach(t -> System.out.println(
                            "  [" + t.getId() + "] " + t.getName()));
                    System.out.print("Select Team ID: ");
                    long teamId = readInt();
                    Team team = teams.stream()
                            .filter(t -> t.getId() == teamId)
                            .findFirst().orElse(null);
                    if (team == null) {
                        System.out.println("✗ Team not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("First Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Last Name: ");
                    String surname = scanner.nextLine().trim();
                    System.out.print("Nationality: ");
                    String nationality = scanner.nextLine().trim();
                    System.out.print("Date of Birth (dd/MM/yyyy): ");
                    LocalDate dateOfBirth = readDate();
                    if (dateOfBirth == null) break;
                    System.out.print("Number of Championships: ");
                    int numberOfChampionships = readInt();

                    try {
                        Driver d = new Driver(null, name, surname, nationality,
                                0, dateOfBirth, numberOfChampionships, team, null);
                        driverService.addDriver(d);
                        System.out.println("✓ Driver added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("\n-- Update Driver --");
                    List<Driver> drivers = driverService.getAllDrivers();
                    if (drivers.isEmpty()) {
                        System.out.println("No drivers found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    drivers.forEach(d -> System.out.println(
                            "  [" + d.getId() + "] " + d.getName() +
                                    " " + d.getSurname() +
                                    " | Points: " + d.getPoints()));
                    System.out.print("Select Driver ID: ");
                    long driverId = readInt();
                    Driver driver = drivers.stream()
                            .filter(d -> d.getId() == driverId)
                            .findFirst().orElse(null);
                    if (driver == null) {
                        System.out.println("✗ Driver not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("New Points: ");
                    int points = readInt();
                    System.out.print("New Number of Championships: ");
                    int championships = readInt();

                    try {
                        driver.setPoints(points);
                        driver.setNumberOfChampionships(championships);
                        driverService.updateDriver(driver);
                        System.out.println("✓ Driver updated successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Delete Driver --");
                    List<Driver> drivers = driverService.getAllDrivers();
                    if (drivers.isEmpty()) {
                        System.out.println("No drivers found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    drivers.forEach(d -> System.out.println(
                            "  [" + d.getId() + "] " + d.getName() +
                                    " " + d.getSurname()));
                    System.out.print("Select Driver ID: ");
                    long driverId = readInt();
                    Driver driver = drivers.stream()
                            .filter(d -> d.getId() == driverId)
                            .findFirst().orElse(null);
                    if (driver == null) {
                        System.out.println("✗ Driver not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    try {
                        driverService.deleteDriver(driver);
                        System.out.println("✓ Driver deleted successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 4 -> {
                    System.out.println("\n-- Driver Standings --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear() +
                                    " | Championship: " + s.getChampionship().getName()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Driver> standings = driverService.getDriverStandings(season);
                    if (standings.isEmpty()) {
                        System.out.println("No drivers found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } else {
                        System.out.println("\nDriver Standings for " + season.getYear() + ":");
                        int position = 1;
                        for (Driver d : standings) {
                            System.out.println("  P" + position++ + " " +
                                    d.getName() + " " + d.getSurname() +
                                    " | " + d.getTeam().getName() +
                                    " | " + d.getPoints() + " pts");
                        }
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 5 -> {
                    System.out.println("\n-- Driver History --");
                    List<Driver> drivers = driverService.getAllDrivers();
                    if (drivers.isEmpty()) {
                        System.out.println("No drivers found.");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    drivers.forEach(d -> System.out.println(
                            "  [" + d.getId() + "] " + d.getName() +
                                    " " + d.getSurname()));
                    System.out.print("Select Driver ID: ");
                    long driverId = readInt();
                    Driver driver = drivers.stream()
                            .filter(d -> d.getId() == driverId)
                            .findFirst().orElse(null);
                    if (driver == null) {
                        System.out.println("✗ Driver not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<RaceResult> history = driverService.getDriverHistory(driver);
                    if (history.isEmpty()) {
                        System.out.println("No race history found for: " +
                                driver.getName() + " " + driver.getSurname());
                    } else {
                        System.out.println("\nRace History for " +
                                driver.getName() + " " + driver.getSurname() + ":");
                        history.forEach(r -> System.out.println(
                                "  " + r.getRace().getName() +
                                        " | P" + r.getPosition() +
                                        " | " + r.getTime() +
                                        " | " + r.getPoints() + " pts"));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the Race management menu.
     */
    private void raceMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- RACE MANAGEMENT ---");
            System.out.println("1. Add Race");
            System.out.println("2. Add Race Result");
            System.out.println("3. View Race Results");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Add Race --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found. Add a season first!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear() +
                                    " | Championship: " + s.getChampionship().getName()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Circuit> circuits = circuitService.getAllCircuits();
                    if (circuits.isEmpty()) {
                        System.out.println("No circuits found. Add a circuit first!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    circuits.forEach(c -> System.out.println(
                            "  [" + c.getId() + "] " + c.getName() +
                                    " | " + c.getCountry()));
                    System.out.print("Select Circuit ID: ");
                    long circuitId = readInt();
                    Circuit circuit = circuits.stream()
                            .filter(c -> c.getId() == circuitId)
                            .findFirst().orElse(null);
                    if (circuit == null) {
                        System.out.println("✗ Circuit not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Race Name: ");
                    String name = scanner.nextLine().trim();
                    System.out.print("Start Date (dd/MM/yyyy): ");
                    LocalDate dateOfBeginning = readDate();
                    if (dateOfBeginning == null) break;
                    System.out.print("End Date (dd/MM/yyyy): ");
                    LocalDate dateOfEnding = readDate();
                    if (dateOfEnding == null) break;
                    System.out.print("Number of Laps: ");
                    int numberOfLaps = readInt();

                    try {
                        Race r = new Race(null, name, dateOfBeginning,
                                dateOfEnding, numberOfLaps, season, circuit, null);
                        raceService.addRace(r);
                        System.out.println("✓ Race added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 2 -> {
                    System.out.println("\n-- Add Race Result --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Race> races = raceService.getRacesBySeason(season);
                    if (races.isEmpty()) {
                        System.out.println("No races found for this season!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    races.forEach(r -> System.out.println(
                            "  [" + r.getId() + "] " + r.getName()));
                    System.out.print("Select Race ID: ");
                    long raceId = readInt();
                    Race race = races.stream()
                            .filter(r -> r.getId() == raceId)
                            .findFirst().orElse(null);
                    if (race == null) {
                        System.out.println("✗ Race not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Driver> drivers = driverService.getDriverStandings(season);
                    if (drivers.isEmpty()) {
                        System.out.println("No drivers found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    drivers.forEach(d -> System.out.println(
                            "  [" + d.getId() + "] " + d.getName() +
                                    " " + d.getSurname()));
                    System.out.print("Select Driver ID: ");
                    long driverId = readInt();
                    Driver driver = drivers.stream()
                            .filter(d -> d.getId() == driverId)
                            .findFirst().orElse(null);
                    if (driver == null) {
                        System.out.println("✗ Driver not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    System.out.print("Position: ");
                    int position = readInt();
                    System.out.print("Time (e.g. 1:20:45.123): ");
                    String time = scanner.nextLine().trim();
                    System.out.print("Points: ");
                    int points = readInt();

                    try {
                        RaceResult result = new RaceResult(null, position,
                                time, points, driver, race);
                        raceService.addRaceResult(result);
                        System.out.println("✓ Race result added successfully!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                    System.out.println("\n-- Race Results --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Race> races = raceService.getRacesBySeason(season);
                    if (races.isEmpty()) {
                        System.out.println("No races found for this season!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    races.forEach(r -> System.out.println(
                            "  [" + r.getId() + "] " + r.getName()));
                    System.out.print("Select Race ID: ");
                    long raceId = readInt();
                    Race race = races.stream()
                            .filter(r -> r.getId() == raceId)
                            .findFirst().orElse(null);
                    if (race == null) {
                        System.out.println("✗ Race not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<RaceResult> results = raceService.getRaceResults(race);
                    if (results.isEmpty()) {
                        System.out.println("No results found for: " + race.getName());
                    } else {
                        System.out.println("\nResults for " + race.getName() + ":");
                        results.forEach(r -> System.out.println(
                                "  P" + r.getPosition() +
                                        " | " + r.getDriver().getName() +
                                        " " + r.getDriver().getSurname() +
                                        " | " + r.getTime() +
                                        " | " + r.getPoints() + " pts"));
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }

    /**
     * Displays and handles the JSON operations menu.
     */
    private void jsonMenu() {
        boolean back = false;
        while (!back) {
            clearConsole();
            System.out.println("\n--- JSON OPERATIONS ---");
            System.out.println("1. Export Standings to JSON");
            System.out.println("2. Import Race Results from JSON");
            System.out.println("0. Back");
            System.out.print("Select option: ");

            switch (readInt()) {
                case 1 -> {
                    System.out.println("\n-- Export Standings to JSON --");
                    List<Season> seasons = seasonService.getActiveSeasons();
                    if (seasons.isEmpty()) {
                        System.out.println("No active seasons found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    seasons.forEach(s -> System.out.println(
                            "  [" + s.getId() + "] " + s.getYear() +
                                    " | Championship: " + s.getChampionship().getName()));
                    System.out.print("Select Season ID: ");
                    long seasonId = readInt();
                    Season season = seasons.stream()
                            .filter(s -> s.getId() == seasonId)
                            .findFirst().orElse(null);
                    if (season == null) {
                        System.out.println("✗ Season not found!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    List<Driver> standings = driverService.getDriverStandings(season);
                    if (standings.isEmpty()) {
                        System.out.println("No drivers found for this season!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                        break;
                    }
                    String filePath = "standings-" + season.getYear() + ".json";
                    try {
                        jsonService.exportStandingsToJson(season, standings, filePath);
                        System.out.println("✓ Standings exported to: " + filePath);
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("\n-- Import Race Results from JSON --");
                    System.out.print("Enter file path: ");
                    String filePath = scanner.nextLine().trim();
                    try {
                        List<RaceResult> results =
                                jsonService.importRaceResultsFromJson(filePath);
                        System.out.println("✓ Successfully imported " +
                                results.size() + " race results!");
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    } catch (Exception e) {
                        System.out.println("✗ Error: " + e.getMessage());
                        System.out.print("\nPress ENTER to continue...");
                        scanner.nextLine();
                    }
                }
                case 0 -> back = true;
                default -> System.out.println("Invalid option, try again.");
            }
        }
    }
}
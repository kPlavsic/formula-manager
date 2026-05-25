package com.formula.manager.service;

import com.formula.manager.model.*;
import com.formula.manager.repository.RaceResultRepository;
import com.formula.manager.repository.RaceRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JsonService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class JsonServiceTest {

    @Mock
    private RaceResultRepository raceResultRepository;

    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private JsonService jsonService;

    private Season season;
    private Championship championship;
    private Driver driver;
    private Team team;
    private List<Driver> drivers;

    /**
     * Sets up valid objects before each test.
     */
    @BeforeEach
    void setUp() {
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
        season = new Season(null, 2024, 20, true,
                LocalDate.of(2024, 3, 1), championship, null);
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
        driver = new Driver(1L, "Max", "Verstappen", "Dutch",
                400, LocalDate.of(1997, 9, 30), 3, team, null);
        drivers = List.of(driver);
    }

    /**
     * Tears down all objects after each test.
     */
    @AfterEach
    void tearDown() {
        season = null;
        championship = null;
        driver = null;
        team = null;
        drivers = null;
    }

    /**
     * Tests that exportStandingsToJson successfully creates a JSON file.
     */
    @Test
    void testExportStandingsToJsonSuccess() {
        String filePath = "test-standings.json";
        assertDoesNotThrow(() -> jsonService.exportStandingsToJson(season, drivers, filePath));
        java.io.File file = new java.io.File(filePath);
        assertTrue(file.exists());
        file.delete();
    }

    /**
     * Tests that exportStandingsToJson with null season throws IllegalArgumentException.
     */
    @Test
    void testExportStandingsNullSeasonThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> jsonService.exportStandingsToJson(null, drivers, "test.json"));
    }

    /**
     * Tests that exportStandingsToJson with null drivers throws IllegalArgumentException.
     */
    @Test
    void testExportStandingsNullDriversThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> jsonService.exportStandingsToJson(season, null, "test.json"));
    }

    /**
     * Tests that importRaceResultsFromJson with null file path throws IllegalArgumentException.
     */
    @Test
    void testImportRaceResultsNullPathThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> jsonService.importRaceResultsFromJson(null));
    }

    /**
     * Tests that importRaceResultsFromJson with empty file path throws IllegalArgumentException.
     */
    @Test
    void testImportRaceResultsEmptyPathThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> jsonService.importRaceResultsFromJson(""));
    }

    /**
     * Tests that importRaceResultsFromJson with non-existent file throws IOException.
     */
    @Test
    void testImportRaceResultsNonExistentFileThrowsIOException() {
        assertThrows(IOException.class,
                () -> jsonService.importRaceResultsFromJson("non-existent-file.json"));
    }
}
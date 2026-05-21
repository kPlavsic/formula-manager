package com.formula.manager.service;

import com.formula.manager.model.*;
import com.formula.manager.repository.RaceRepository;
import com.formula.manager.repository.RaceResultRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for RaceService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class RaceServiceTest {

    @Mock
    private RaceRepository raceRepository;

    @Mock
    private RaceResultRepository raceResultRepository;

    @InjectMocks
    private RaceService raceService;

    private Race race;
    private RaceResult raceResult;
    private Season season;
    private Circuit circuit;
    private Championship championship;
    private Driver driver;
    private Team team;

    /**
     * Sets up valid objects before each test.
     */
    @BeforeEach
    void setUp() {
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
        season = new Season(null, 2024, 20, true,
                LocalDate.of(2024, 3, 1), championship, null);
        circuit = new Circuit(null, "Monza Circuit", "Italy", 5.793, 11, 100000);
        race = new Race(null, "Italian Grand Prix",
                LocalDate.of(2024, 9, 1),
                LocalDate.of(2024, 9, 3),
                53, season, circuit, null);
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
        driver = new Driver(null, "Max", "Verstappen", "Dutch",
                400, LocalDate.of(1997, 9, 30), 3, team, null);
        raceResult = new RaceResult(null, 1, "1:20:45.123", 25, driver, race);
    }

    /**
     * Tears down all objects after each test.
     */
    @AfterEach
    void tearDown() {
        race = null;
        raceResult = null;
        season = null;
        circuit = null;
        championship = null;
        driver = null;
        team = null;
    }

    /**
     * Tests that a valid race is saved successfully.
     */
    @Test
    void testAddRaceSuccess() {
        when(raceRepository.save(race)).thenReturn(race);
        Race result = raceService.addRace(race);
        assertNotNull(result);
        assertEquals(race.getName(), result.getName());
        verify(raceRepository, times(1)).save(race);
    }

    /**
     * Tests that adding a null race throws IllegalArgumentException.
     */
    @Test
    void testAddNullRaceThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> raceService.addRace(null));
        verify(raceRepository, never()).save(any());
    }

    /**
     * Tests that adding a race without season throws IllegalArgumentException.
     */
    @Test
    void testAddRaceWithoutSeasonThrowsException() {
        race.setSeason(null);
        assertThrows(IllegalArgumentException.class,
                () -> raceService.addRace(race));
        verify(raceRepository, never()).save(any());
    }

    /**
     * Tests that a valid race result is saved successfully.
     */
    @Test
    void testAddRaceResultSuccess() {
        when(raceResultRepository.save(raceResult)).thenReturn(raceResult);
        RaceResult result = raceService.addRaceResult(raceResult);
        assertNotNull(result);
        assertEquals(raceResult.getPosition(), result.getPosition());
        verify(raceResultRepository, times(1)).save(raceResult);
    }

    /**
     * Tests that adding a null race result throws IllegalArgumentException.
     */
    @Test
    void testAddNullRaceResultThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> raceService.addRaceResult(null));
        verify(raceResultRepository, never()).save(any());
    }

    /**
     * Tests that adding a race result without driver throws IllegalArgumentException.
     */
    @Test
    void testAddRaceResultWithoutDriverThrowsException() {
        raceResult.setDriver(null);
        assertThrows(IllegalArgumentException.class,
                () -> raceService.addRaceResult(raceResult));
        verify(raceResultRepository, never()).save(any());
    }

    /**
     * Tests that adding a race result without race throws IllegalArgumentException.
     */
    @Test
    void testAddRaceResultWithoutRaceThrowsException() {
        raceResult.setRace(null);
        assertThrows(IllegalArgumentException.class,
                () -> raceService.addRaceResult(raceResult));
        verify(raceResultRepository, never()).save(any());
    }

    /**
     * Tests that getRaceResults returns list of results ordered by position.
     */
    @Test
    void testGetRaceResultsReturnsResults() {
        when(raceResultRepository.findByRaceOrderByPositionAsc(race))
                .thenReturn(List.of(raceResult));
        List<RaceResult> result = raceService.getRaceResults(race);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(raceResultRepository, times(1)).findByRaceOrderByPositionAsc(race);
    }

    /**
     * Tests that getRaceResults with null race throws IllegalArgumentException.
     */
    @Test
    void testGetRaceResultsNullRaceThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> raceService.getRaceResults(null));
        verify(raceResultRepository, never()).findByRaceOrderByPositionAsc(any());
    }

    /**
     * Tests that getRacesBySeason returns list of races for a season.
     */
    @Test
    void testGetRacesBySeasonReturnsRaces() {
        when(raceRepository.findBySeason(season)).thenReturn(List.of(race));
        List<Race> result = raceService.getRacesBySeason(season);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(raceRepository, times(1)).findBySeason(season);
    }

    /**
     * Tests that getRacesBySeason returns empty list when no races exist.
     */
    @Test
    void testGetRacesBySeasonReturnsEmptyList() {
        when(raceRepository.findBySeason(season)).thenReturn(List.of());
        List<Race> result = raceService.getRacesBySeason(season);
        assertTrue(result.isEmpty());
        verify(raceRepository, times(1)).findBySeason(season);
    }
}
package com.formula.manager.service;

import com.formula.manager.model.*;
import com.formula.manager.repository.DriverRepository;
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
 * Unit tests for DriverService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class DriverServiceTest {

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private RaceResultRepository raceResultRepository;

    @Mock
    private RaceRepository raceRepository;

    @InjectMocks
    private DriverService driverService;

    private Driver driver;
    private Team team;
    private Season season;
    private Championship championship;
    private RaceResult raceResult;

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
        raceResult = new RaceResult(null, 1, "1:20:45.123", 25, driver, null);
    }

    /**
     * Tears down all objects after each test.
     */
    @AfterEach
    void tearDown() {
        driver = null;
        team = null;
        season = null;
        championship = null;
        raceResult = null;
    }

    /**
     * Tests that a valid driver is saved successfully.
     */
    @Test
    void testAddDriverSuccess() {
        when(driverRepository.save(driver)).thenReturn(driver);
        Driver result = driverService.addDriver(driver);
        assertNotNull(result);
        assertEquals(driver.getName(), result.getName());
        verify(driverRepository, times(1)).save(driver);
    }

    /**
     * Tests that adding a null driver throws IllegalArgumentException.
     */
    @Test
    void testAddNullDriverThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> driverService.addDriver(null));
        verify(driverRepository, never()).save(any());
    }

    /**
     * Tests that adding a driver without team throws IllegalArgumentException.
     */
    @Test
    void testAddDriverWithoutTeamThrowsException() {
        driver.setTeam(null);
        assertThrows(IllegalArgumentException.class,
                () -> driverService.addDriver(driver));
        verify(driverRepository, never()).save(any());
    }

    /**
     * Tests that a valid driver is updated successfully.
     */
    @Test
    void testUpdateDriverSuccess() {
        when(driverRepository.save(driver)).thenReturn(driver);
        Driver result = driverService.updateDriver(driver);
        assertNotNull(result);
        assertEquals(driver.getName(), result.getName());
        verify(driverRepository, times(1)).save(driver);
    }

    /**
     * Tests that updating a null driver throws IllegalArgumentException.
     */
    @Test
    void testUpdateNullDriverThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> driverService.updateDriver(null));
        verify(driverRepository, never()).save(any());
    }

    /**
     * Tests that updating a driver without ID throws IllegalArgumentException.
     */
    @Test
    void testUpdateDriverWithoutIdThrowsException() {
        driver.setId(null);
        assertThrows(IllegalArgumentException.class,
                () -> driverService.updateDriver(driver));
        verify(driverRepository, never()).save(any());
    }

    /**
     * Tests that a valid driver is deleted successfully.
     */
    @Test
    void testDeleteDriverSuccess() {

        team.setDrivers(new java.util.ArrayList<>(List.of(driver)));

        when(driverRepository.findById(1L))
                .thenReturn(java.util.Optional.of(driver));

        driverService.deleteDriver(driver);

        verify(driverRepository, times(1))
                .findById(1L);

        assertFalse(team.getDrivers().contains(driver));
    }

    /**
     * Tests that deleting a null driver throws IllegalArgumentException.
     */
    @Test
    void testDeleteNullDriverThrowsException() {
        assertThrows(
                NullPointerException.class,
                () -> driverService.deleteDriver(null)
        );

        verify(driverRepository, never())
                .findById(any());
    }


    /**
     * Tests that getDriverStandings returns list of drivers ordered by points.
     */
    @Test
    void testGetDriverStandingsReturnsDrivers() {
        when(driverRepository.findAllByOrderByPointsDesc()).thenReturn(List.of(driver));
        List<Driver> result = driverService.getDriverStandings(season);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(driverRepository, times(1)).findAllByOrderByPointsDesc();
    }

    /**
     * Tests that getDriverStandings with null season throws IllegalArgumentException.
     */
    @Test
    void testGetDriverStandingsNullSeasonThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> driverService.getDriverStandings(null));
        verify(driverRepository, never()).findAllByOrderByPointsDesc();
    }

    /**
     * Tests that getDriverHistory returns list of race results for a driver.
     */
    @Test
    void testGetDriverHistoryReturnsResults() {
        when(raceResultRepository.findByDriver(driver)).thenReturn(List.of(raceResult));
        List<RaceResult> result = driverService.getDriverHistory(driver);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(raceResultRepository, times(1)).findByDriver(driver);
    }

    /**
     * Tests that getDriverHistory with null driver throws IllegalArgumentException.
     */
    @Test
    void testGetDriverHistoryNullDriverThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> driverService.getDriverHistory(null));
        verify(raceResultRepository, never()).findByDriver(any());
    }
}
package com.formula.manager.service;

import com.formula.manager.model.*;
import com.formula.manager.repository.DriverRepository;
import com.formula.manager.repository.TeamRepository;
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
 * Unit tests for TeamService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @Mock
    private DriverRepository driverRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private Driver driver;
    private Season season;
    private Championship championship;

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
        team.setDrivers(List.of(driver));
    }

    /**
     * Tears down all objects after each test.
     */
    @AfterEach
    void tearDown() {
        team = null;
        driver = null;
        season = null;
        championship = null;
    }

    /**
     * Tests that a valid team is saved successfully.
     */
    @Test
    void testAddTeamSuccess() {
        when(teamRepository.save(team)).thenReturn(team);
        Team result = teamService.addTeam(team);
        assertNotNull(result);
        assertEquals(team.getName(), result.getName());
        verify(teamRepository, times(1)).save(team);
    }

    /**
     * Tests that adding a null team throws IllegalArgumentException.
     */
    @Test
    void testAddNullTeamThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> teamService.addTeam(null));
        verify(teamRepository, never()).save(any());
    }

    /**
     * Tests that getTeamStandings returns list of teams ordered by points.
     */
    @Test
    void testGetTeamStandingsReturnsTeams() {
        when(teamRepository.findAll()).thenReturn(List.of(team));
        List<Team> result = teamService.getTeamStandings(season);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findAll();
    }

    /**
     * Tests that getTeamStandings with null season throws IllegalArgumentException.
     */
    @Test
    void testGetTeamStandingsNullSeasonThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> teamService.getTeamStandings(null));
        verify(teamRepository, never()).findAll();
    }

    /**
     * Tests that getAllTeams returns list of teams.
     */
    @Test
    void testGetAllTeamsReturnsAll() {
        when(teamRepository.findAll()).thenReturn(List.of(team));
        List<Team> result = teamService.getAllTeams();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(teamRepository, times(1)).findAll();
    }

    /**
     * Tests that getAllTeams returns empty list when no teams exist.
     */
    @Test
    void testGetAllTeamsReturnsEmptyList() {
        when(teamRepository.findAll()).thenReturn(List.of());
        List<Team> result = teamService.getAllTeams();
        assertTrue(result.isEmpty());
        verify(teamRepository, times(1)).findAll();
    }
}
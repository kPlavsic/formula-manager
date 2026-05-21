package com.formula.manager.service;

import com.formula.manager.model.Championship;
import com.formula.manager.model.Season;
import com.formula.manager.repository.SeasonRepository;
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
 * Unit tests for SeasonService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class SeasonServiceTest {

    @Mock
    private SeasonRepository seasonRepository;

    @InjectMocks
    private SeasonService seasonService;

    private Season season;
    private Championship championship;

    /**
     * Sets up a valid season before each test.
     */
    @BeforeEach
    void setUp() {
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
        season = new Season(null, 2024, 20, true,
                LocalDate.of(2024, 3, 1), championship, null);
    }

    /**
     * Tears down the season after each test.
     */
    @AfterEach
    void tearDown() {
        season = null;
        championship = null;
    }

    /**
     * Tests that a valid season is saved successfully.
     */
    @Test
    void testAddSeasonSuccess() {
        when(seasonRepository.save(season)).thenReturn(season);
        Season result = seasonService.addSeason(season);
        assertNotNull(result);
        assertEquals(season.getYear(), result.getYear());
        verify(seasonRepository, times(1)).save(season);
    }

    /**
     * Tests that adding a null season throws IllegalArgumentException.
     */
    @Test
    void testAddNullSeasonThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> seasonService.addSeason(null));
        verify(seasonRepository, never()).save(any());
    }

    /**
     * Tests that adding a season without championship throws IllegalArgumentException.
     */
    @Test
    void testAddSeasonWithoutChampionshipThrowsException() {
        season.setChampionship(null);
        assertThrows(IllegalArgumentException.class,
                () -> seasonService.addSeason(season));
        verify(seasonRepository, never()).save(any());
    }

    /**
     * Tests that getSeasonsByChampionship returns list of seasons.
     */
    @Test
    void testGetSeasonsByChampionshipReturnsSeasons() {
        when(seasonRepository.findByChampionship(championship)).thenReturn(List.of(season));
        List<Season> result = seasonService.getSeasonsByChampionship(championship);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(seasonRepository, times(1)).findByChampionship(championship);
    }

    /**
     * Tests that getSeasonsByChampionship returns empty list when no seasons exist.
     */
    @Test
    void testGetSeasonsByChampionshipReturnsEmptyList() {
        when(seasonRepository.findByChampionship(championship)).thenReturn(List.of());
        List<Season> result = seasonService.getSeasonsByChampionship(championship);
        assertTrue(result.isEmpty());
        verify(seasonRepository, times(1)).findByChampionship(championship);
    }

    /**
     * Tests that getActiveSeasons returns list of active seasons.
     */
    @Test
    void testGetActiveSeasonsReturnsActiveSeasons() {
        when(seasonRepository.findByActive(true)).thenReturn(List.of(season));
        List<Season> result = seasonService.getActiveSeasons();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(seasonRepository, times(1)).findByActive(true);
    }

    /**
     * Tests that getActiveSeasons returns empty list when no active seasons exist.
     */
    @Test
    void testGetActiveSeasonsReturnsEmptyList() {
        when(seasonRepository.findByActive(true)).thenReturn(List.of());
        List<Season> result = seasonService.getActiveSeasons();
        assertTrue(result.isEmpty());
        verify(seasonRepository, times(1)).findByActive(true);
    }
}
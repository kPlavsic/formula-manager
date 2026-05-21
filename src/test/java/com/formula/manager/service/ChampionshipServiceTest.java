package com.formula.manager.service;

import com.formula.manager.model.Championship;
import com.formula.manager.repository.ChampionshipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ChampionshipService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class ChampionshipServiceTest {

    @Mock
    private ChampionshipRepository championshipRepository;

    @InjectMocks
    private ChampionshipService championshipService;

    private Championship championship;

    /**
     * Sets up a valid championship before each test.
     */
    @BeforeEach
    void setUp() {
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
    }

    /**
     * Tears down the championship after each test.
     */
    @AfterEach
    void tearDown() {
        championship = null;
    }

    /**
     * Tests that a valid championship is saved successfully.
     */
    @Test
    void testAddChampionshipSuccess() {
        when(championshipRepository.save(championship)).thenReturn(championship);
        Championship result = championshipService.addChampionship(championship);
        assertNotNull(result);
        assertEquals(championship.getName(), result.getName());
        verify(championshipRepository, times(1)).save(championship);
    }

    /**
     * Tests that adding a null championship throws IllegalArgumentException.
     */
    @Test
    void testAddNullChampionshipThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> championshipService.addChampionship(null));
        verify(championshipRepository, never()).save(any());
    }

    /**
     * Tests that getAllChampionships returns a list of championships.
     */
    @Test
    void testGetAllChampionshipsReturnsAll() {
        when(championshipRepository.findAll()).thenReturn(List.of(championship));
        List<Championship> result = championshipService.getAllChampionships();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(championshipRepository, times(1)).findAll();
    }

    /**
     * Tests that getAllChampionships returns empty list when no championships exist.
     */
    @Test
    void testGetAllChampionshipsReturnsEmptyList() {
        when(championshipRepository.findAll()).thenReturn(List.of());
        List<Championship> result = championshipService.getAllChampionships();
        assertTrue(result.isEmpty());
        verify(championshipRepository, times(1)).findAll();
    }
}
package com.formula.manager.service;

import com.formula.manager.model.Circuit;
import com.formula.manager.repository.CircuitRepository;
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
 * Unit tests for CircuitService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class CircuitServiceTest {

    @Mock
    private CircuitRepository circuitRepository;

    @InjectMocks
    private CircuitService circuitService;

    private Circuit circuit;

    /**
     * Sets up a valid circuit before each test.
     */
    @BeforeEach
    void setUp() {
        circuit = new Circuit(null, "Monza Circuit", "Italy", 5.793, 11, 100000);
    }

    /**
     * Tears down the circuit after each test.
     */
    @AfterEach
    void tearDown() {
        circuit = null;
    }

    /**
     * Tests that a valid circuit is saved successfully.
     */
    @Test
    void testAddCircuitSuccess() {
        when(circuitRepository.save(circuit)).thenReturn(circuit);
        Circuit result = circuitService.addCircuit(circuit);
        assertNotNull(result);
        assertEquals(circuit.getName(), result.getName());
        verify(circuitRepository, times(1)).save(circuit);
    }

    /**
     * Tests that adding a null circuit throws IllegalArgumentException.
     */
    @Test
    void testAddNullCircuitThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> circuitService.addCircuit(null));
        verify(circuitRepository, never()).save(any());
    }

    /**
     * Tests that getAllCircuits returns a list of circuits.
     */
    @Test
    void testGetAllCircuitsReturnsAll() {
        when(circuitRepository.findAll()).thenReturn(List.of(circuit));
        List<Circuit> result = circuitService.getAllCircuits();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(circuitRepository, times(1)).findAll();
    }

    /**
     * Tests that getAllCircuits returns empty list when no circuits exist.
     */
    @Test
    void testGetAllCircuitsReturnsEmptyList() {
        when(circuitRepository.findAll()).thenReturn(List.of());
        List<Circuit> result = circuitService.getAllCircuits();
        assertTrue(result.isEmpty());
        verify(circuitRepository, times(1)).findAll();
    }

    /**
     * Tests that getCircuitsByCountry returns circuits for a given country.
     */
    @Test
    void testGetCircuitsByCountryReturnsCircuits() {
        when(circuitRepository.findByCountry("Italy")).thenReturn(List.of(circuit));
        List<Circuit> result = circuitService.getCircuitsByCountry("Italy");
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(circuitRepository, times(1)).findByCountry("Italy");
    }

    /**
     * Tests that getCircuitsByCountry returns empty list when no circuits exist for country.
     */
    @Test
    void testGetCircuitsByCountryReturnsEmptyList() {
        when(circuitRepository.findByCountry("Germany")).thenReturn(List.of());
        List<Circuit> result = circuitService.getCircuitsByCountry("Germany");
        assertTrue(result.isEmpty());
        verify(circuitRepository, times(1)).findByCountry("Germany");
    }
}
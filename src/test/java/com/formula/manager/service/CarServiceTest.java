package com.formula.manager.service;

import com.formula.manager.model.*;
import com.formula.manager.repository.CarRepository;
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
 * Unit tests for CarService.
 *
 * @author kPlavsic
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car;
    private Team team;

    /**
     * Sets up valid objects before each test.
     */
    @BeforeEach
    void setUp() {
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
        car = new Car(null, "RB20", "Honda RBPT", 2024, 1000, 798.0, team);
    }

    /**
     * Tears down all objects after each test.
     */
    @AfterEach
    void tearDown() {
        car = null;
        team = null;
    }

    /**
     * Tests that a valid car is saved successfully.
     */
    @Test
    void testAddCarSuccess() {
        when(carRepository.save(car)).thenReturn(car);
        Car result = carService.addCar(car);
        assertNotNull(result);
        assertEquals(car.getModel(), result.getModel());
        verify(carRepository, times(1)).save(car);
    }

    /**
     * Tests that adding a null car throws IllegalArgumentException.
     */
    @Test
    void testAddNullCarThrowsException() {
        assertThrows(IllegalArgumentException.class,
                () -> carService.addCar(null));
        verify(carRepository, never()).save(any());
    }

    /**
     * Tests that adding a car without team throws IllegalArgumentException.
     */
    @Test
    void testAddCarWithoutTeamThrowsException() {
        car.setTeam(null);
        assertThrows(IllegalArgumentException.class,
                () -> carService.addCar(car));
        verify(carRepository, never()).save(any());
    }

    /**
     * Tests that getCarsByTeam returns list of cars for a team.
     */
    @Test
    void testGetCarsByTeamReturnsCars() {
        when(carRepository.findByTeam(team)).thenReturn(List.of(car));
        List<Car> result = carService.getCarsByTeam(team);
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carRepository, times(1)).findByTeam(team);
    }

    /**
     * Tests that getCarsByTeam returns empty list when no cars exist for team.
     */
    @Test
    void testGetCarsByTeamReturnsEmptyList() {
        when(carRepository.findByTeam(team)).thenReturn(List.of());
        List<Car> result = carService.getCarsByTeam(team);
        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findByTeam(team);
    }

    /**
     * Tests that getAllCars returns list of all cars.
     */
    @Test
    void testGetAllCarsReturnsAll() {
        when(carRepository.findAll()).thenReturn(List.of(car));
        List<Car> result = carService.getAllCars();
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(carRepository, times(1)).findAll();
    }

    /**
     * Tests that getAllCars returns empty list when no cars exist.
     */
    @Test
    void testGetAllCarsReturnsEmptyList() {
        when(carRepository.findAll()).thenReturn(List.of());
        List<Car> result = carService.getAllCars();
        assertTrue(result.isEmpty());
        verify(carRepository, times(1)).findAll();
    }
}
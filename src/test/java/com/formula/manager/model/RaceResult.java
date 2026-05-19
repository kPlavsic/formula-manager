package com.formula.manager.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for RaceResult model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class RaceResultTest {

    private Validator validator;
    private RaceResult raceResult;
    private Driver driver;
    private Race race;
    private Team team;
    private Season season;
    private Circuit circuit;
    private Championship championship;

    /**
     * Sets up the validator and a valid race result before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
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
     * Tears down the validator and race result after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        raceResult = null;
        driver = null;
        race = null;
        team = null;
        season = null;
        circuit = null;
        championship = null;
    }

    /**
     * Tests that a valid race result passes validation.
     */
    @Test
    void testValidRaceResult() {
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero position fails validation.
     */
    @Test
    void testZeroPositionFails() {
        raceResult.setPosition(0);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that negative position fails validation.
     */
    @Test
    void testNegativePositionFails() {
        raceResult.setPosition(-1);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that exactly position 1 passes validation (boundary case).
     */
    @Test
    void testExactlyOnePositionPasses() {
        raceResult.setPosition(1);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null time fails validation.
     */
    @Test
    void testNullTimeFails() {
        raceResult.setTime(null);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a blank time fails validation.
     */
    @Test
    void testBlankTimeFails() {
        raceResult.setTime("");
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a whitespace-only time fails validation.
     */
    @Test
    void testWhitespaceTimeFails() {
        raceResult.setTime("   ");
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that negative points fails validation.
     */
    @Test
    void testNegativePointsFails() {
        raceResult.setPoints(-1);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that zero points passes validation (boundary case).
     */
    @Test
    void testZeroPointsPasses() {
        raceResult.setPoints(0);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null driver fails validation.
     */
    @Test
    void testNullDriverFails() {
        raceResult.setDriver(null);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a null race fails validation.
     */
    @Test
    void testNullRaceFails() {
        raceResult.setRace(null);
        Set<ConstraintViolation<RaceResult>> violations = validator.validate(raceResult);
        assertFalse(violations.isEmpty());
    }
}
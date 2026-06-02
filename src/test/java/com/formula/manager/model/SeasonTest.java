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
 * Unit tests for Season model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class SeasonTest {

    private Validator validator;
    private Season season;
    private Championship championship;

    /**
     * Verifies that a validation violation exists for the specified field
     * and that the expected validation message is present.
     *
     * @param violations validation violations returned by the validator
     * @param fieldName field expected to contain the violation
     * @param expectedMessage expected validation message
     * @param <T> validated object type
     */
    private <T> void assertViolation(
            Set<ConstraintViolation<T>> violations,
            String fieldName,
            String expectedMessage) {

        assertFalse(violations.isEmpty());

        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals(fieldName)
                                && v.getMessage().equals(expectedMessage)
                )
        );
    }

    /**
     * Sets up the validator and a valid season before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
        season = new Season(null, 2024, 20, true, LocalDate.of(2024, 3, 1), championship, null);
    }

    /**
     * Tears down the validator and season after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        season = null;
        championship = null;
    }

    /**
     * Tests that a valid season passes validation.
     */
    @Test
    void testValidSeason() {
        Set<ConstraintViolation<Season>> violations = validator.validate(season);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that exactly year 1950 passes validation (boundary case).
     */
    @Test
    void testYearExactly1950Passes() {
        season.setYear(1950);
        Set<ConstraintViolation<Season>> violations = validator.validate(season);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that exactly 1 race passes validation (boundary case).
     */
    @Test
    void testExactlyOneRacePasses() {
        season.setNumberOfRaces(1);
        Set<ConstraintViolation<Season>> violations = validator.validate(season);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a year before 1950 fails validation.
     */
    @Test
    void testYearBefore1950Fails() {
        season.setYear(1949);

        Set<ConstraintViolation<Season>> violations =
                validator.validate(season);

        assertViolation(
                violations,
                "year",
                "Season year cannot be before 1950");
    }

    /**
     * Tests that zero number of races fails validation.
     */
    @Test
    void testZeroNumberOfRacesFails() {
        season.setNumberOfRaces(0);

        Set<ConstraintViolation<Season>> violations =
                validator.validate(season);

        assertViolation(
                violations,
                "numberOfRaces",
                "Number of races must be positive");
    }

    /**
     * Tests that a negative number of races fails validation.
     */
    @Test
    void testNegativeNumberOfRacesFails() {
        season.setNumberOfRaces(-1);

        Set<ConstraintViolation<Season>> violations =
                validator.validate(season);

        assertViolation(
                violations,
                "numberOfRaces",
                "Number of races must be positive");
    }

    /**
     * Tests that a null start time fails validation.
     */
    @Test
    void testNullStartTimeFails() {
        season.setStartTime(null);

        Set<ConstraintViolation<Season>> violations =
                validator.validate(season);

        assertViolation(
                violations,
                "startTime",
                "Start time cannot be null");
    }

    /**
     * Tests that a null championship fails validation.
     */
    @Test
    void testNullChampionshipFails() {
        season.setChampionship(null);

        Set<ConstraintViolation<Season>> violations =
                validator.validate(season);

        assertViolation(
                violations,
                "championship",
                "Championship cannot be null");
    }

}
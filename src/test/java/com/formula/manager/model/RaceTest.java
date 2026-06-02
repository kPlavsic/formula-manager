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
 * Unit tests for Race model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class RaceTest {

    private Validator validator;
    private Race race;
    private Season season;
    private Circuit circuit;
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

        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals(fieldName)
                                && v.getMessage().equals(expectedMessage)
                )
        );
    }

    /**
     * Sets up the validator and a valid race before each test.
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
    }

    /**
     * Tears down the validator and race after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        race = null;
        season = null;
        circuit = null;
        championship = null;
    }

    /**
     * Tests that a valid race passes validation.
     */
    @Test
    void testValidRace() {
        Set<ConstraintViolation<Race>> violations = validator.validate(race);
        assertTrue(violations.isEmpty());
    }


    /**
     * Tests that a name of exactly 3 characters passes validation (boundary case).
     */
    @Test
    void testNameExactlyMinLengthPasses() {
        race.setName("GP1");
        Set<ConstraintViolation<Race>> violations = validator.validate(race);
        assertTrue(violations.isEmpty());
    }


    /**
     * Tests that exactly 1 lap passes validation (boundary case).
     */
    @Test
    void testExactlyOneLapPasses() {
        race.setNumberOfLaps(1);
        Set<ConstraintViolation<Race>> violations = validator.validate(race);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null name fails validation.
     */
    @Test
    void testNullNameFails() {
        race.setName(null);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "name",
                "Race name cannot be blank");
    }

    /**
     * Tests that a blank name fails validation.
     */
    @Test
    void testBlankNameFails() {
        race.setName("");

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "name",
                "Race name cannot be blank");
    }

    /**
     * Tests that a whitespace-only name fails validation.
     */
    @Test
    void testWhitespaceNameFails() {
        race.setName("   ");

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "name",
                "Race name cannot be blank");
    }

    /**
     * Tests that a name shorter than 3 characters fails validation.
     */
    @Test
    void testNameTooShortFails() {
        race.setName("GP");

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "name",
                "Race name must be between 3 and 100 characters");
    }

    /**
     * Tests that a name longer than 100 characters fails validation.
     */
    @Test
    void testNameTooLongFails() {
        race.setName("A".repeat(101));

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "name",
                "Race name must be between 3 and 100 characters");
    }

    /**
     * Tests that a null date of beginning fails validation.
     */
    @Test
    void testNullDateOfBeginningFails() {
        race.setDateOfBeginning(null);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "dateOfBeginning",
                "Date of beginning cannot be null");
    }

    /**
     * Tests that a null date of ending fails validation.
     */
    @Test
    void testNullDateOfEndingFails() {
        race.setDateOfEnding(null);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "dateOfEnding",
                "Date of ending cannot be null");
    }

    /**
     * Tests that zero number of laps fails validation.
     */
    @Test
    void testZeroNumberOfLapsFails() {
        race.setNumberOfLaps(0);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "numberOfLaps",
                "Number of laps must be positive");
    }

    /**
     * Tests that negative number of laps fails validation.
     */
    @Test
    void testNegativeNumberOfLapsFails() {
        race.setNumberOfLaps(-1);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "numberOfLaps",
                "Number of laps must be positive");
    }

    /**
     * Tests that a null season fails validation.
     */
    @Test
    void testNullSeasonFails() {
        race.setSeason(null);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "season",
                "Season cannot be null");
    }

    /**
     * Tests that a null circuit fails validation.
     */
    @Test
    void testNullCircuitFails() {
        race.setCircuit(null);

        Set<ConstraintViolation<Race>> violations =
                validator.validate(race);

        assertViolation(
                violations,
                "circuit",
                "Circuit cannot be null");
    }
}
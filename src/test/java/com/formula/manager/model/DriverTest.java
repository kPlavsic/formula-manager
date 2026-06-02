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
 * Unit tests for Driver model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class DriverTest {

    private Validator validator;
    private Driver driver;
    private Team team;

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
     * Sets up the validator and a valid driver before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
        driver = new Driver(null, "Max", "Verstappen", "Dutch",
                400, LocalDate.of(1997, 9, 30), 3, team, null);
    }

    /**
     * Tears down the validator and driver after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        driver = null;
        team = null;
    }

    /**
     * Tests that a valid driver passes validation.
     */
    @Test
    void testValidDriver() {
        Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
        assertTrue(violations.isEmpty());
    }


    /**
     * Tests that a name of exactly 2 characters passes validation (boundary case).
     */
    @Test
    void testNameExactlyMinLengthPasses() {
        driver.setName("MX");
        Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a surname of exactly 2 characters passes validation (boundary case).
     */
    @Test
    void testSurnameExactlyMinLengthPasses() {
        driver.setSurname("VE");
        Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero points passes validation (boundary case).
     */
    @Test
    void testZeroPointsPasses() {
        driver.setPoints(0);
        Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero number of championships passes validation (boundary case).
     */
    @Test
    void testZeroNumberOfChampionshipsPasses() {
        driver.setNumberOfChampionships(0);
        Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null name fails validation.
     */
    @Test
    void testNullNameFails() {
        driver.setName(null);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "name",
                "Driver name cannot be blank");
    }

    /**
     * Tests that a blank name fails validation.
     */
    @Test
    void testBlankNameFails() {
        driver.setName("");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "name",
                "Driver name cannot be blank");
    }

    /**
     * Tests that a whitespace-only name fails validation.
     */
    @Test
    void testWhitespaceNameFails() {
        driver.setName("   ");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "name",
                "Driver name cannot be blank");
    }

    /**
     * Tests that a name shorter than 2 characters fails validation.
     */
    @Test
    void testNameTooShortFails() {
        driver.setName("M");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "name",
                "Driver name must be between 2 and 50 characters");
    }

    /**
     * Tests that a name longer than 50 characters fails validation.
     */
    @Test
    void testNameTooLongFails() {
        driver.setName("A".repeat(51));

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "name",
                "Driver name must be between 2 and 50 characters");
    }

    /**
     * Tests that a null surname fails validation.
     */
    @Test
    void testNullSurnameFails() {
        driver.setSurname(null);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "surname",
                "Driver surname cannot be blank");
    }

    /**
     * Tests that a blank surname fails validation.
     */
    @Test
    void testBlankSurnameFails() {
        driver.setSurname("");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "surname",
                "Driver surname cannot be blank");
    }

    /**
     * Tests that a whitespace-only surname fails validation.
     */
    @Test
    void testWhitespaceSurnameFails() {
        driver.setSurname("   ");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "surname",
                "Driver surname cannot be blank");
    }

    /**
     * Tests that a surname shorter than 2 characters fails validation.
     */
    @Test
    void testSurnameTooShortFails() {
        driver.setSurname("V");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "surname",
                "Driver surname must be between 2 and 50 characters");
    }

    /**
     * Tests that a surname longer than 50 characters fails validation.
     */
    @Test
    void testSurnameTooLongFails() {
        driver.setSurname("A".repeat(51));

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "surname",
                "Driver surname must be between 2 and 50 characters");
    }

    /**
     * Tests that a null nationality fails validation.
     */
    @Test
    void testNullNationalityFails() {
        driver.setNationality(null);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "nationality",
                "Nationality cannot be blank");
    }

    /**
     * Tests that a blank nationality fails validation.
     */
    @Test
    void testBlankNationalityFails() {
        driver.setNationality("");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "nationality",
                "Nationality cannot be blank");
    }

    /**
     * Tests that a whitespace-only nationality fails validation.
     */
    @Test
    void testWhitespaceNationalityFails() {
        driver.setNationality("   ");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "nationality",
                "Nationality cannot be blank");
    }

    /**
     * Tests that negative points fails validation.
     */
    @Test
    void testNegativePointsFails() {
        driver.setPoints(-1);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "points",
                "Points cannot be negative");
    }

    /**
     * Tests that a null date of birth fails validation.
     */
    @Test
    void testNullDateOfBirthFails() {
        driver.setDateOfBirth(null);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "dateOfBirth",
                "Date of birth cannot be null");
    }

    /**
     * Tests that a date of birth in the future fails validation.
     */
    @Test
    void testFutureDateOfBirthFails() {
        driver.setDateOfBirth(LocalDate.now().plusDays(1));

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "dateOfBirth",
                "Date of birth must be in the past");
    }

    /**
     * Tests that negative number of championships fails validation.
     */
    @Test
    void testNegativeNumberOfChampionshipsFails() {
        driver.setNumberOfChampionships(-1);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "numberOfChampionships",
                "Number of championships cannot be negative");
    }

    /**
     * Tests that a null team fails validation.
     */
    @Test
    void testNullTeamFails() {
        driver.setTeam(null);

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertViolation(
                violations,
                "team",
                "Team cannot be null");
    }

    /**
     * Tests that a non-blank nationality passes validation.
     */
    @Test
    void testValidNationalityPasses() {
        driver.setNationality("Dutch");

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a past date of birth passes validation.
     */
    @Test
    void testPastDateOfBirthPasses() {
        driver.setDateOfBirth(LocalDate.of(1997, 9, 30));

        Set<ConstraintViolation<Driver>> violations =
                validator.validate(driver);

        assertTrue(violations.isEmpty());
    }
}
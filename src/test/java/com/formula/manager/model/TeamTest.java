package com.formula.manager.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Team model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class TeamTest {

    private Validator validator;
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
     * Sets up the validator and a valid team before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
    }

    /**
     * Tears down the validator and team after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        team = null;
    }

    /**
     * Tests that a valid team passes validation.
     */
    @Test
    void testValidTeam() {
        Set<ConstraintViolation<Team>> violations = validator.validate(team);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a name of exactly 3 characters passes validation (boundary case).
     */
    @Test
    void testNameExactlyMinLengthPasses() {
        team.setName("RBR");
        Set<ConstraintViolation<Team>> violations = validator.validate(team);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero budget passes validation (boundary case).
     */
    @Test
    void testZeroBudgetPasses() {
        team.setBudget(0.0);
        Set<ConstraintViolation<Team>> violations = validator.validate(team);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that exactly year 1950 passes validation (boundary case).
     */
    @Test
    void testYearOfFormingExactly1950Passes() {
        team.setYearOfForming(1950);
        Set<ConstraintViolation<Team>> violations = validator.validate(team);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null name fails validation.
     */
    @Test
    void testNullNameFails() {
        team.setName(null);

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "name",
                "Team name cannot be blank");
    }

    /**
     * Tests that a blank name fails validation.
     */
    @Test
    void testBlankNameFails() {
        team.setName("");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "name",
                "Team name cannot be blank");
    }

    /**
     * Tests that a whitespace-only name fails validation.
     */
    @Test
    void testWhitespaceNameFails() {
        team.setName("   ");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "name",
                "Team name cannot be blank");
    }

    /**
     * Tests that a name shorter than 3 characters fails validation.
     */
    @Test
    void testNameTooShortFails() {
        team.setName("RB");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "name",
                "Team name must be between 3 and 100 characters");
    }

    /**
     * Tests that a name longer than 100 characters fails validation.
     */
    @Test
    void testNameTooLongFails() {
        team.setName("A".repeat(101));

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "name",
                "Team name must be between 3 and 100 characters");
    }

    /**
     * Tests that a null country fails validation.
     */
    @Test
    void testNullCountryFails() {
        team.setCountry(null);

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "country",
                "Country cannot be blank");
    }

    /**
     * Tests that a blank country fails validation.
     */
    @Test
    void testBlankCountryFails() {
        team.setCountry("");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "country",
                "Country cannot be blank");
    }

    /**
     * Tests that a whitespace-only country fails validation.
     */
    @Test
    void testWhitespaceCountryFails() {
        team.setCountry("   ");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "country",
                "Country cannot be blank");
    }

    /**
     * Tests that negative budget fails validation.
     */
    @Test
    void testNegativeBudgetFails() {
        team.setBudget(-1.0);

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "budget",
                "Budget cannot be negative");
    }

    /**
     * Tests that a null team principal fails validation.
     */
    @Test
    void testNullTeamPrincipalFails() {
        team.setTeamPrincipal(null);

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "teamPrincipal",
                "Team principal cannot be blank");
    }

    /**
     * Tests that a blank team principal fails validation.
     */
    @Test
    void testBlankTeamPrincipalFails() {
        team.setTeamPrincipal("");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "teamPrincipal",
                "Team principal cannot be blank");
    }

    /**
     * Tests that a whitespace-only team principal fails validation.
     */
    @Test
    void testWhitespaceTeamPrincipalFails() {
        team.setTeamPrincipal("   ");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "teamPrincipal",
                "Team principal cannot be blank");
    }

    /**
     * Tests that a year of forming before 1950 fails validation.
     */
    @Test
    void testYearOfFormingBefore1950Fails() {
        team.setYearOfForming(1949);

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertViolation(
                violations,
                "yearOfForming",
                "Year of forming cannot be before 1950");
    }

    /**
     * Tests that a non-blank country passes validation.
     */
    @Test
    void testValidCountryPasses() {
        team.setCountry("Austria");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a non-blank team principal passes validation.
     */
    @Test
    void testValidTeamPrincipalPasses() {
        team.setTeamPrincipal("Christian Horner");

        Set<ConstraintViolation<Team>> violations =
                validator.validate(team);

        assertTrue(violations.isEmpty());
    }
}
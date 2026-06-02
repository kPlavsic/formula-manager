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
 * Unit tests for Championship model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class ChampionshipTest {

    private Validator validator;
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
     * Sets up the validator and a valid championship before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        championship = new Championship(null, "Formula 1 World Championship",
                "Formula 1", 2024, 1000000.0, "FIA", null);
    }

    /**
     * Tears down the validator and championship after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        championship = null;
    }

    /**
     * Tests that a valid championship passes validation.
     */
    @Test
    void testValidChampionship() {
        Set<ConstraintViolation<Championship>> violations = validator.validate(championship);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null name fails validation.
     */
    @Test
    void testNullNameFails() {
        championship.setName(null);

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "name",
                "Championship name cannot be blank");
    }

    /**
     * Tests that a blank name fails validation.
     */
    @Test
    void testBlankNameFails() {
        championship.setName("");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "name",
                "Championship name cannot be blank");
    }

    /**
     * Tests that a whitespace-only name fails validation.
     */
    @Test
    void testWhitespaceNameFails() {
        championship.setName("   ");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "name",
                "Championship name cannot be blank");
    }

    /**
     * Tests that a name shorter than 3 characters fails validation.
     */
    @Test
    void testNameTooShortFails() {
        championship.setName("F1");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "name",
                "Championship name must be between 3 and 100 characters");
    }

    /**
     * Tests that a name longer than 100 characters fails validation.
     */
    @Test
    void testNameTooLongFails() {
        championship.setName("A".repeat(101));

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "name",
                "Championship name must be between 3 and 100 characters");
    }

    /**
     * Tests that a name of exactly 3 characters passes validation (boundary case).
     */
    @Test
    void testNameExactlyMinLengthPasses() {
        championship.setName("F1W");
        Set<ConstraintViolation<Championship>> violations = validator.validate(championship);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null type fails validation.
     */
    @Test
    void testNullTypeFails() {
        championship.setType(null);

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "type",
                "Championship type cannot be blank");
    }

    /**
     * Tests that a blank type fails validation.
     */
    @Test
    void testBlankTypeFails() {
        championship.setType("");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "type",
                "Championship type cannot be blank");
    }

    /**
     * Tests that a whitespace-only type fails validation.
     */
    @Test
    void testWhitespaceTypeFails() {
        championship.setType("   ");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "type",
                "Championship type cannot be blank");
    }

    /**
     * Tests that a year before 1950 fails validation.
     */
    @Test
    void testYearBefore1950Fails() {
        championship.setYear(1949);

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "year",
                "Championship year cannot be before 1950");
    }

    /**
     * Tests that exactly year 1950 passes validation (boundary case).
     */
    @Test
    void testYearExactly1950Passes() {
        championship.setYear(1950);
        Set<ConstraintViolation<Championship>> violations = validator.validate(championship);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that negative prize money fails validation.
     */
    @Test
    void testNegativePrizeMoneyFails() {
        championship.setPrizeMoney(-1.0);

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "prizeMoney",
                "Prize money cannot be negative");
    }

    /**
     * Tests that zero prize money passes validation (boundary case).
     */
    @Test
    void testZeroPrizeMoneyPasses() {
        championship.setPrizeMoney(0.0);
        Set<ConstraintViolation<Championship>> violations = validator.validate(championship);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null organizer fails validation.
     */
    @Test
    void testNullOrganizerFails() {
        championship.setOrganizer(null);

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "organizer",
                "Organizer cannot be blank");
    }

    /**
     * Tests that a blank organizer fails validation.
     */
    @Test
    void testBlankOrganizerFails() {
        championship.setOrganizer("");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "organizer",
                "Organizer cannot be blank");
    }

    /**
     * Tests that a whitespace-only organizer fails validation.
     */
    @Test
    void testWhitespaceOrganizerFails() {
        championship.setOrganizer("   ");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertViolation(
                violations,
                "organizer",
                "Organizer cannot be blank");
    }

    /**
     * Tests that a non-blank type passes validation.
     */
    @Test
    void testValidTypePasses() {
        championship.setType("F1");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a non-blank organizer passes validation.
     */
    @Test
    void testValidOrganizerPasses() {
        championship.setOrganizer("FIA");

        Set<ConstraintViolation<Championship>> violations =
                validator.validate(championship);

        assertTrue(violations.isEmpty());
    }

}
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
 * Unit tests for Car model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class CarTest {

    private Validator validator;
    private Car car;
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

        assertTrue(
                violations.stream().anyMatch(v ->
                        v.getPropertyPath().toString().equals(fieldName)
                                && v.getMessage().equals(expectedMessage)
                )
        );
    }

    /**
     * Sets up the validator and a valid car before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        team = new Team(null, "Red Bull Racing", "Austria",
                500000000.0, "Christian Horner", 1992, null, null);
        car = new Car(null, "RB20", "Honda RBPT", 2024, 1000, 798.0, team);
    }

    /**
     * Tears down the validator and car after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        car = null;
        team = null;
    }

    /**
     * Tests that a valid car passes validation.
     */
    @Test
    void testValidCar() {
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null model fails validation.
     */
    @Test
    void testNullModelFails() {
        car.setModel(null);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "model",
                "Car model cannot be blank");
    }

    /**
     * Tests that a blank model fails validation.
     */
    @Test
    void testBlankModelFails() {
        car.setModel("");

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "model",
                "Car model cannot be blank");
    }

    /**
     * Tests that a whitespace-only model fails validation.
     */
    @Test
    void testWhitespaceModelFails() {
        car.setModel("   ");

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "model",
                "Car model cannot be blank");
    }

    /**
     * Tests that a model shorter than 2 characters fails validation.
     */
    @Test
    void testModelTooShortFails() {
        car.setModel("R");

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "model",
                "Car model must be between 2 and 50 characters");
    }

    /**
     * Tests that a model longer than 50 characters fails validation.
     */
    @Test
    void testModelTooLongFails() {
        car.setModel("A".repeat(51));

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "model",
                "Car model must be between 2 and 50 characters");
    }

    /**
     * Tests that a model of exactly 2 characters passes validation (boundary case).
     */
    @Test
    void testModelExactlyMinLengthPasses() {
        car.setModel("RB");
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null motor fails validation.
     */
    @Test
    void testNullMotorFails() {
        car.setMotor(null);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "motor",
                "Motor cannot be blank");
    }

    /**
     * Tests that a blank motor fails validation.
     */
    @Test
    void testBlankMotorFails() {
        car.setMotor("");

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "motor",
                "Motor cannot be blank");
    }

    /**
     * Tests that a whitespace-only motor fails validation.
     */
    @Test
    void testWhitespaceMotorFails() {
        car.setMotor("   ");

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "motor",
                "Motor cannot be blank");
    }

    /**
     * Tests that a year before 1950 fails validation.
     */
    @Test
    void testYearBefore1950Fails() {
        car.setYear(1949);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "year",
                "Car year cannot be before 1950");
    }

    /**
     * Tests that exactly year 1950 passes validation (boundary case).
     */
    @Test
    void testYearExactly1950Passes() {
        car.setYear(1950);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero horsepower fails validation.
     */
    @Test
    void testZeroHorsePowerFails() {
        car.setHorsePower(0);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "horsePower",
                "Horsepower must be positive");
    }

    /**
     * Tests that negative horsepower fails validation.
     */
    @Test
    void testNegativeHorsePowerFails() {
        car.setHorsePower(-1);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "horsePower",
                "Horsepower must be positive");
    }

    /**
     * Tests that exactly 1 horsepower passes validation (boundary case).
     */
    @Test
    void testExactlyOneHorsePowerPasses() {
        car.setHorsePower(1);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero weight fails validation.
     */
    @Test
    void testZeroWeightFails() {
        car.setWeight(0);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "weight",
                "Weight must be positive");
    }

    /**
     * Tests that negative weight fails validation.
     */
    @Test
    void testNegativeWeightFails() {
        car.setWeight(-1);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "weight",
                "Weight must be positive");
    }
    /**
     * Tests that a weight of 0.1 passes validation (boundary case).
     */
    @Test
    void testMinimalWeightPasses() {
        car.setWeight(0.1);
        Set<ConstraintViolation<Car>> violations = validator.validate(car);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null team fails validation.
     */
    @Test
    void testNullTeamFails() {
        car.setTeam(null);

        Set<ConstraintViolation<Car>> violations =
                validator.validate(car);

        assertViolation(
                violations,
                "team",
                "Team cannot be null");
    }
}
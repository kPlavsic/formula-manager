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
 * Unit tests for Circuit model validation.
 *
 * @author kPlavsic
 * @version 1.0
 */
class CircuitTest {

    private Validator validator;
    private Circuit circuit;

    /**
     * Sets up the validator and a valid circuit before each test.
     */
    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        circuit = new Circuit(null, "Monza Circuit", "Italy", 5.793, 11, 100000);
    }

    /**
     * Tears down the validator and circuit after each test.
     */
    @AfterEach
    void tearDown() {
        validator = null;
        circuit = null;
    }

    /**
     * Tests that a valid circuit passes validation.
     */
    @Test
    void testValidCircuit() {
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null name fails validation.
     */
    @Test
    void testNullNameFails() {
        circuit.setName(null);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a blank name fails validation.
     */
    @Test
    void testBlankNameFails() {
        circuit.setName("");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a whitespace-only name fails validation.
     */
    @Test
    void testWhitespaceNameFails() {
        circuit.setName("   ");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a name shorter than 3 characters fails validation.
     */
    @Test
    void testNameTooShortFails() {
        circuit.setName("Mo");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a name longer than 100 characters fails validation.
     */
    @Test
    void testNameTooLongFails() {
        circuit.setName("A".repeat(101));
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a name of exactly 3 characters passes validation (boundary case).
     */
    @Test
    void testNameExactlyMinLengthPasses() {
        circuit.setName("Mon");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that a null country fails validation.
     */
    @Test
    void testNullCountryFails() {
        circuit.setCountry(null);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a blank country fails validation.
     */
    @Test
    void testBlankCountryFails() {
        circuit.setCountry("");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a whitespace-only country fails validation.
     */
    @Test
    void testWhitespaceCountryFails() {
        circuit.setCountry("   ");
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that zero length fails validation.
     */
    @Test
    void testZeroLengthFails() {
        circuit.setLength(0.0);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that negative length fails validation.
     */
    @Test
    void testNegativeLengthFails() {
        circuit.setLength(-1.0);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that a length of 0.1 passes validation (boundary case).
     */
    @Test
    void testMinimalLengthPasses() {
        circuit.setLength(0.1);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero number of turns fails validation.
     */
    @Test
    void testZeroNumberOfTurnsFails() {
        circuit.setNumberOfTurns(0);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that negative number of turns fails validation.
     */
    @Test
    void testNegativeNumberOfTurnsFails() {
        circuit.setNumberOfTurns(-1);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that exactly 1 turn passes validation (boundary case).
     */
    @Test
    void testExactlyOneTurnPasses() {
        circuit.setNumberOfTurns(1);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertTrue(violations.isEmpty());
    }

    /**
     * Tests that zero capacity fails validation.
     */
    @Test
    void testZeroCapacityFails() {
        circuit.setCapacity(0);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that negative capacity fails validation.
     */
    @Test
    void testNegativeCapacityFails() {
        circuit.setCapacity(-1);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertFalse(violations.isEmpty());
    }

    /**
     * Tests that exactly 1 capacity passes validation (boundary case).
     */
    @Test
    void testExactlyOneCapacityPasses() {
        circuit.setCapacity(1);
        Set<ConstraintViolation<Circuit>> violations = validator.validate(circuit);
        assertTrue(violations.isEmpty());
    }
}
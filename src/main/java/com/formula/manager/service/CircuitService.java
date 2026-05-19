package com.formula.manager.service;

import com.formula.manager.model.Circuit;
import com.formula.manager.repository.CircuitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Circuit business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class CircuitService {

    /**
     * Repository for Circuit entity operations.
     */
    @Autowired
    private CircuitRepository circuitRepository;

    /**
     * Adds a new circuit to the system.
     *
     * @param circuit the circuit to add
     * @return the saved circuit with generated ID
     * @throws IllegalArgumentException if circuit is null
     */
    public Circuit addCircuit(Circuit circuit) {
        if (circuit == null) {
            throw new IllegalArgumentException("Circuit cannot be null");
        }
        return circuitRepository.save(circuit);
    }

    /**
     * Retrieves all circuits from the system.
     *
     * @return list of all circuits
     */
    public List<Circuit> getAllCircuits() {
        return circuitRepository.findAll();
    }

    /**
     * Retrieves all circuits located in a specific country.
     *
     * @param country the country to search by
     * @return list of circuits in that country
     */
    public List<Circuit> getCircuitsByCountry(String country) {
        return circuitRepository.findByCountry(country);
    }
}
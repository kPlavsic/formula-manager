package com.formula.manager.repository;

import com.formula.manager.model.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Circuit entity.
 * Provides CRUD operations and custom queries for Circuit.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface CircuitRepository extends JpaRepository<Circuit, Long> {

    /**
     * Finds all circuits located in a specific country.
     *
     * @param country the country to search by
     * @return list of circuits in that country
     */
    List<Circuit> findByCountry(String country);

    /**
     * Finds a circuit by its exact name.
     *
     * @param name the name of the circuit
     * @return the circuit with that name
     */
    Circuit findByName(String name);
}
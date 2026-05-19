package com.formula.manager.repository;

import com.formula.manager.model.Race;
import com.formula.manager.model.Season;
import com.formula.manager.model.Circuit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Race entity.
 * Provides CRUD operations and custom queries for Race.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    /**
     * Finds all races belonging to a specific season.
     *
     * @param season the season to search by
     * @return list of races in that season
     */
    List<Race> findBySeason(Season season);

    /**
     * Finds all races held on a specific circuit.
     *
     * @param circuit the circuit to search by
     * @return list of races on that circuit
     */
    List<Race> findByCircuit(Circuit circuit);

    /**
     * Finds a race by its exact name.
     *
     * @param name the name of the race
     * @return the race with that name
     */
    Race findByName(String name);
}
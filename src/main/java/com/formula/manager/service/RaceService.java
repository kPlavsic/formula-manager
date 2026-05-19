package com.formula.manager.service;

import com.formula.manager.model.Race;
import com.formula.manager.model.RaceResult;
import com.formula.manager.model.Season;
import com.formula.manager.repository.RaceRepository;
import com.formula.manager.repository.RaceResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Race and RaceResult business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class RaceService {

    /**
     * Repository for Race entity operations.
     */
    @Autowired
    private RaceRepository raceRepository;

    /**
     * Repository for RaceResult entity operations.
     */
    @Autowired
    private RaceResultRepository raceResultRepository;

    /**
     * Adds a new race to the system.
     * Race must belong to an existing season.
     *
     * @param race the race to add
     * @return the saved race with generated ID
     * @throws IllegalArgumentException if race is null or has no season
     */
    public Race addRace(Race race) {
        if (race == null) {
            throw new IllegalArgumentException("Race cannot be null");
        }
        if (race.getSeason() == null) {
            throw new IllegalArgumentException("Race must belong to a season");
        }
        return raceRepository.save(race);
    }

    /**
     * Adds a new race result to the system.
     *
     * @param result the race result to add
     * @return the saved race result with generated ID
     * @throws IllegalArgumentException if result is null, has no driver or no race
     */
    public RaceResult addRaceResult(RaceResult result) {
        if (result == null) {
            throw new IllegalArgumentException("RaceResult cannot be null");
        }
        if (result.getDriver() == null) {
            throw new IllegalArgumentException("RaceResult must have a driver");
        }
        if (result.getRace() == null) {
            throw new IllegalArgumentException("RaceResult must have a race");
        }
        return raceResultRepository.save(result);
    }

    /**
     * Retrieves all results for a specific race ordered by position.
     *
     * @param race the race to get results for
     * @return list of race results ordered by position ascending
     * @throws IllegalArgumentException if race is null
     */
    public List<RaceResult> getRaceResults(Race race) {
        if (race == null) {
            throw new IllegalArgumentException("Race cannot be null");
        }
        return raceResultRepository.findByRaceOrderByPositionAsc(race);
    }

    /**
     * Retrieves all races belonging to a specific season.
     *
     * @param season the season to search by
     * @return list of races in that season
     */
    public List<Race> getRacesBySeason(Season season) {
        return raceRepository.findBySeason(season);
    }
}
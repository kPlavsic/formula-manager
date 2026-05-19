package com.formula.manager.service;

import com.formula.manager.model.Driver;
import com.formula.manager.model.RaceResult;
import com.formula.manager.model.Season;
import com.formula.manager.repository.DriverRepository;
import com.formula.manager.repository.RaceResultRepository;
import com.formula.manager.repository.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Driver business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class DriverService {

    /**
     * Repository for Driver entity operations.
     */
    @Autowired
    private DriverRepository driverRepository;

    /**
     * Repository for RaceResult entity operations.
     */
    @Autowired
    private RaceResultRepository raceResultRepository;

    /**
     * Repository for Race entity operations.
     */
    @Autowired
    private RaceRepository raceRepository;

    /**
     * Adds a new driver to the system.
     *
     * @param driver the driver to add
     * @return the saved driver with generated ID
     * @throws IllegalArgumentException if driver is null or has no team
     */
    public Driver addDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (driver.getTeam() == null) {
            throw new IllegalArgumentException("Driver must belong to a team");
        }
        return driverRepository.save(driver);
    }

    /**
     * Updates an existing driver in the system.
     *
     * @param driver the driver with updated information
     * @return the updated driver
     * @throws IllegalArgumentException if driver is null or has no ID
     */
    public Driver updateDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (driver.getId() == null) {
            throw new IllegalArgumentException("Driver must have an ID to be updated");
        }
        return driverRepository.save(driver);
    }

    /**
     * Deletes a driver from the system.
     *
     * @param driver the driver to delete
     * @throws IllegalArgumentException if driver is null or has no ID
     */
    public void deleteDriver(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        if (driver.getId() == null) {
            throw new IllegalArgumentException("Driver must have an ID to be deleted");
        }
        driverRepository.delete(driver);
    }

    /**
     * Retrieves driver standings for a specific season,
     * ordered by total points descending.
     *
     * @param season the season to get standings for
     * @return list of drivers ordered by points descending
     * @throws IllegalArgumentException if season is null
     */
    public List<Driver> getDriverStandings(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        return driverRepository.findAllByOrderByPointsDesc();
    }

    /**
     * Retrieves the complete race history for a specific driver.
     *
     * @param driver the driver to get history for
     * @return list of all race results for that driver
     * @throws IllegalArgumentException if driver is null
     */
    public List<RaceResult> getDriverHistory(Driver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("Driver cannot be null");
        }
        return raceResultRepository.findByDriver(driver);
    }
}
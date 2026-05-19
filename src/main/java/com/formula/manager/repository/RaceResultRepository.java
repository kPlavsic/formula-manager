package com.formula.manager.repository;

import com.formula.manager.model.RaceResult;
import com.formula.manager.model.Driver;
import com.formula.manager.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for RaceResult entity.
 * Provides CRUD operations and custom queries for RaceResult.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface RaceResultRepository extends JpaRepository<RaceResult, Long> {

    /**
     * Finds all results for a specific race ordered by position.
     *
     * @param race the race to search by
     * @return list of results for that race ordered by position
     */
    List<RaceResult> findByRaceOrderByPositionAsc(Race race);

    /**
     * Finds all results for a specific driver.
     *
     * @param driver the driver to search by
     * @return list of results for that driver
     */
    List<RaceResult> findByDriver(Driver driver);

    /**
     * Finds a specific result for a driver in a specific race.
     *
     * @param driver the driver to search by
     * @param race the race to search by
     * @return the result of that driver in that race
     */
    RaceResult findByDriverAndRace(Driver driver, Race race);
}
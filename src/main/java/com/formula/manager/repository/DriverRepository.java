package com.formula.manager.repository;

import com.formula.manager.model.Driver;
import com.formula.manager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Driver entity.
 * Provides CRUD operations and custom queries for Driver.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {

    /**
     * Finds all drivers belonging to a specific team.
     *
     * @param team the team to search by
     * @return list of drivers in that team
     */
    List<Driver> findByTeam(Team team);

    /**
     * Finds all drivers by nationality.
     *
     * @param nationality the nationality to search by
     * @return list of drivers with that nationality
     */
    List<Driver> findByNationality(String nationality);

    /**
     * Finds a driver by first and last name.
     *
     * @param name the first name of the driver
     * @param surname the last name of the driver
     * @return the driver with that name
     */
    Driver findByNameAndSurname(String name, String surname);

    /**
     * Finds all drivers ordered by points descending.
     *
     * @return list of drivers sorted by points
     */
    List<Driver> findAllByOrderByPointsDesc();
}
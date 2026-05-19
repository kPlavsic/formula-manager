package com.formula.manager.repository;

import com.formula.manager.model.Car;
import com.formula.manager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Car entity.
 * Provides CRUD operations and custom queries for Car.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Finds all cars belonging to a specific team.
     *
     * @param team the team to search by
     * @return list of cars in that team
     */
    List<Car> findByTeam(Team team);

    /**
     * Finds all cars by motor type.
     *
     * @param motor the motor type to search by
     * @return list of cars with that motor
     */
    List<Car> findByMotor(String motor);

    /**
     * Finds all cars manufactured in a specific year.
     *
     * @param year the year to search by
     * @return list of cars from that year
     */
    List<Car> findByYear(int year);
}
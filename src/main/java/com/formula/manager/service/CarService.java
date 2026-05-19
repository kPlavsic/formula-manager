package com.formula.manager.service;

import com.formula.manager.model.Car;
import com.formula.manager.model.Team;
import com.formula.manager.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Car business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class CarService {

    /**
     * Repository for Car entity operations.
     */
    @Autowired
    private CarRepository carRepository;

    /**
     * Adds a new car to the system.
     * Car must belong to an existing team.
     *
     * @param car the car to add
     * @return the saved car with generated ID
     * @throws IllegalArgumentException if car is null or has no team
     */
    public Car addCar(Car car) {
        if (car == null) {
            throw new IllegalArgumentException("Car cannot be null");
        }
        if (car.getTeam() == null) {
            throw new IllegalArgumentException("Car must belong to a team");
        }
        return carRepository.save(car);
    }

    /**
     * Retrieves all cars belonging to a specific team.
     *
     * @param team the team to search by
     * @return list of cars in that team
     */
    public List<Car> getCarsByTeam(Team team) {
        return carRepository.findByTeam(team);
    }

    /**
     * Retrieves all cars from the system.
     *
     * @return list of all cars
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
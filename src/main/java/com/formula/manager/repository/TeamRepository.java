package com.formula.manager.repository;

import com.formula.manager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Team entity.
 * Provides CRUD operations and custom queries for Team.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    /**
     * Finds all teams from a specific country.
     *
     * @param country the country to search by
     * @return list of teams from that country
     */
    List<Team> findByCountry(String country);

    /**
     * Finds a team by its exact name.
     *
     * @param name the name of the team
     * @return the team with that name
     */
    Team findByName(String name);

    /**
     * Finds all teams formed in a specific year.
     *
     * @param yearOfForming the year the team was formed
     * @return list of teams formed in that year
     */
    List<Team> findByYearOfForming(int yearOfForming);
}
package com.formula.manager.service;

import com.formula.manager.model.Team;
import com.formula.manager.model.Driver;
import com.formula.manager.model.Season;
import com.formula.manager.repository.TeamRepository;
import com.formula.manager.repository.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Team business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class TeamService {

    /**
     * Repository for Team entity operations.
     */
    @Autowired
    private TeamRepository teamRepository;

    /**
     * Repository for Driver entity operations.
     */
    @Autowired
    private DriverRepository driverRepository;

    /**
     * Adds a new team to the system.
     *
     * @param team the team to add
     * @return the saved team with generated ID
     * @throws IllegalArgumentException if team is null
     */
    public Team addTeam(Team team) {
        if (team == null) {
            throw new IllegalArgumentException("Team cannot be null");
        }
        return teamRepository.save(team);
    }

    /**
     * Retrieves team standings for a specific season.
     * Teams are ranked by the total points of their drivers.
     *
     * @param season the season to get standings for
     * @return list of teams ordered by total points descending
     * @throws IllegalArgumentException if season is null
     */
    public List<Team> getTeamStandings(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        return teamRepository.findAll().stream()
                .sorted((t1, t2) -> {
                    int points1 = t1.getDrivers().stream()
                            .mapToInt(Driver::getPoints).sum();
                    int points2 = t2.getDrivers().stream()
                            .mapToInt(Driver::getPoints).sum();
                    return Integer.compare(points2, points1);
                })
                .toList();
    }

    /**
     * Retrieves all teams from the system.
     *
     * @return list of all teams
     */
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }
}
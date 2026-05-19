package com.formula.manager.service;

import com.formula.manager.model.Season;
import com.formula.manager.model.Championship;
import com.formula.manager.repository.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Season business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class SeasonService {

    /**
     * Repository for Season entity operations.
     */
    @Autowired
    private SeasonRepository seasonRepository;

    /**
     * Adds a new season to the system.
     * Season must belong to an existing championship.
     *
     * @param season the season to add
     * @return the saved season with generated ID
     * @throws IllegalArgumentException if season is null or has no championship
     */
    public Season addSeason(Season season) {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        if (season.getChampionship() == null) {
            throw new IllegalArgumentException("Season must belong to a championship");
        }
        return seasonRepository.save(season);
    }

    /**
     * Retrieves all seasons belonging to a specific championship.
     *
     * @param championship the championship to search by
     * @return list of seasons in that championship
     */
    public List<Season> getSeasonsByChampionship(Championship championship) {
        return seasonRepository.findByChampionship(championship);
    }

    /**
     * Retrieves all active seasons.
     *
     * @return list of currently active seasons
     */
    public List<Season> getActiveSeasons() {
        return seasonRepository.findByActive(true);
    }
}
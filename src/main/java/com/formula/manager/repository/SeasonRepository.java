package com.formula.manager.repository;

import com.formula.manager.model.Season;
import com.formula.manager.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Season entity.
 * Provides CRUD operations and custom queries for Season.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {

    /**
     * Finds all seasons belonging to a specific championship.
     *
     * @param championship the championship to search by
     * @return list of seasons in that championship
     */
    List<Season> findByChampionship(Championship championship);

    /**
     * Finds all currently active seasons.
     *
     * @param active true to find active seasons, false for inactive
     * @return list of active/inactive seasons
     */
    List<Season> findByActive(boolean active);

    /**
     * Finds all seasons by year.
     *
     * @param year the year to search for
     * @return list of seasons in that year
     */
    List<Season> findByYear(int year);
}
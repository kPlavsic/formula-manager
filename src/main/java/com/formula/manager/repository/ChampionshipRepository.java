package com.formula.manager.repository;

import com.formula.manager.model.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Repository interface for Championship entity.
 * Provides CRUD operations and custom queries for Championship.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Long> {

    /**
     * Finds all championships by a specific year.
     *
     * @param year the year to search for
     * @return list of championships in that year
     */
    List<Championship> findByYear(int year);

    /**
     * Finds all championships by organizer name.
     *
     * @param organizer the organizer name to search for
     * @return list of championships by that organizer
     */
    List<Championship> findByOrganizer(String organizer);
}
package com.formula.manager.service;

import com.formula.manager.model.Championship;
import com.formula.manager.repository.ChampionshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Service class for Championship business logic.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
@Transactional
public class ChampionshipService {

    /**
     * Repository for Championship entity operations.
     */
    @Autowired
    private ChampionshipRepository championshipRepository;

    /**
     * Adds a new championship to the system.
     *
     * @param championship the championship to add
     * @return the saved championship with generated ID
     * @throws IllegalArgumentException if championship is null
     */
    public Championship addChampionship(Championship championship) {
        if (championship == null) {
            throw new IllegalArgumentException("Championship cannot be null");
        }
        return championshipRepository.save(championship);
    }

    /**
     * Retrieves all championships from the system.
     *
     * @return list of all championships
     */
    public List<Championship> getAllChampionships() {
        return championshipRepository.findAll();
    }
}
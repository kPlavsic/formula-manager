package com.formula.manager.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.formula.manager.model.Driver;
import com.formula.manager.model.Race;
import com.formula.manager.model.RaceResult;
import com.formula.manager.model.Season;
import com.formula.manager.repository.RaceRepository;
import com.formula.manager.repository.RaceResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Service class for JSON import and export operations.
 *
 * @author kPlavsic
 * @version 1.0
 */
@Service
public class JsonService {

    /**
     * Repository for Race entity operations.
     */
    @Autowired
    private RaceRepository raceRepository;

    /**
     * Repository for RaceResult entity operations.
     */
    @Autowired
    private RaceResultRepository raceResultRepository;

    /**
     * Jackson ObjectMapper used for JSON serialization and deserialization.
     */
    private final ObjectMapper objectMapper;

    /**
     * Constructor that configures ObjectMapper with JavaTimeModule
     * to support LocalDate serialization.
     */
    public JsonService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /**
     * Exports driver standings for a specific season to a JSON file.
     *
     * @param season the season whose standings to export
     * @param drivers the list of drivers ordered by points
     * @param filePath the path where the JSON file will be saved
     * @throws IOException if the file cannot be written
     * @throws IllegalArgumentException if season or drivers list is null
     */
    public void exportStandingsToJson(Season season, List<Driver> drivers, String filePath) throws IOException {
        if (season == null) {
            throw new IllegalArgumentException("Season cannot be null");
        }
        if (drivers == null) {
            throw new IllegalArgumentException("Drivers list cannot be null");
        }

        Map<String, Object> standings = new HashMap<>();
        standings.put("season", season.getYear());
        standings.put("championship", season.getChampionship().getName());
        standings.put("drivers", drivers.stream().map(d -> {
            Map<String, Object> driverMap = new HashMap<>();
            driverMap.put("name", d.getName() + " " + d.getSurname());
            driverMap.put("team", d.getTeam().getName());
            driverMap.put("points", d.getPoints());
            driverMap.put("nationality", d.getNationality());
            return driverMap;
        }).toList());

        objectMapper.writeValue(new File(filePath), standings);
    }

    /**
     * Imports race results from a JSON file and saves them to the database.
     *
     * @param filePath the path to the JSON file to import
     * @return list of imported race results
     * @throws IOException if the file cannot be read
     * @throws IllegalArgumentException if filePath is null or empty
     */
    public List<RaceResult> importRaceResultsFromJson(String filePath) throws IOException {
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty");
        }

        RaceResult[] results = objectMapper.readValue(new File(filePath), RaceResult[].class);
        return raceResultRepository.saveAll(List.of(results));
    }
}
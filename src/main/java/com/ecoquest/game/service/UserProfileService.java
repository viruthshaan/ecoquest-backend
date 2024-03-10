package com.ecoquest.game.service;

import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.time.Month;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.DateOperators.DayOfMonth;
import org.springframework.stereotype.Service;

import com.ecoquest.game.exception.ResourceNotFoundException;
import com.ecoquest.game.model.UserProfile;
import com.ecoquest.game.model.Building;

import com.ecoquest.game.repository.UserProfileRepository;
import com.fasterxml.jackson.databind.util.JSONPObject;

@Service
public class UserProfileService {

    @Autowired
    UserProfileRepository userProfileRepository;

    public UserProfile updateMcqMarks(String user_name, int totalMarks) {
        UserProfile existingProfile = userProfileRepository.findByUserName(user_name);

        if (existingProfile != null) {
            existingProfile.setTotalCoins(totalMarks);
            return userProfileRepository.save(existingProfile);
        } else {
            // Handle the case when the user profile is not found
            throw new ResourceNotFoundException("User profile not found with name " + user_name);
        }
    }

    public UserProfile updateCoins(String user_name, String token) {
        UserProfile existingProfile = userProfileRepository.findByUserName(user_name);
        if (existingProfile != null) {
            // System.out.println("came here: " + existingProfile);
            LocalDate lastUpdated = existingProfile.getLastUpdated();
            System.out.println("last updated: " + lastUpdated);
            int totalCoins = existingProfile.getTotalCoins();
            List<Integer> dailyCoins = existingProfile.getCoinsPerDay();
            List<Building> investmentBuildings = existingProfile.getInvestmentBuildings();
            List<Building> energySources = existingProfile.getEnergySources();
            Double expectedUnits = 8.0;

            int expectedEPDFromInvestmentBuildings = 0;
            // System.out.println("came one here: " + energySources);

            for (Building building : investmentBuildings) {

                expectedEPDFromInvestmentBuildings += building.getExpectedEarnings();
            }
            int expectedEPDFromEnergySources = 0;
            for (Building building : energySources) {

                expectedEPDFromEnergySources += building.getExpectedEarnings();

            }
            // System.out.println("came second here: " + expectedEPDFromEnergySources);

            LocalDate currentDate = LocalDate.now();
            Month calledMonth = lastUpdated.getMonth();
            String jsonResponse = HttpService.SendRequest(token, lastUpdated.getYear(),
                    lastUpdated.getMonth().name().toUpperCase());
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject dailyUnits = jsonObject.getJSONObject("dailyPowerConsumptionView").getJSONObject("dailyUnits");

            while (!lastUpdated.isEqual(currentDate.minusDays(1))) {

                if (calledMonth == lastUpdated.getMonth()) {
                    int dayOfMonth = lastUpdated.getDayOfMonth();
                    String dayOfMonthString = String.valueOf(dayOfMonth);
                    double dayUsage = dailyUnits.getDouble(dayOfMonthString);

                    // for checking purposes
                    System.out.println(dayOfMonthString);
                    System.out.println(dayUsage);

                    double todayCoinsDouble = (dayUsage - expectedUnits) * expectedEPDFromInvestmentBuildings
                            + expectedEPDFromEnergySources;
                    int todayCoins = (int) todayCoinsDouble;
                    dailyCoins.add(todayCoins);
                    totalCoins += todayCoins;
                    lastUpdated = lastUpdated.plusDays(1);

                } else {
                    calledMonth = lastUpdated.getMonth();
                    jsonResponse = HttpService.SendRequest(token, lastUpdated.getYear(),
                            lastUpdated.getMonth().name().toUpperCase());
                    jsonObject = new JSONObject(jsonResponse);
                    dailyUnits = jsonObject.getJSONObject("dailyPowerConsumptionView").getJSONObject("dailyUnits");

                }

            }
            existingProfile.setTotalCoins(totalCoins);
            existingProfile.setLastUpdated(lastUpdated);
            existingProfile.setCoinsPerDay(dailyCoins);

            return userProfileRepository.save(existingProfile);
        } else {
            // Handle the case when the user profile is not found
            throw new ResourceNotFoundException("User profile not found with name " + user_name);
        }
    }

}

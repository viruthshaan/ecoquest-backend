package com.ecoquest.game.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Document(collection = "UserProfile")
@Data

public class UserProfile {

    @Id
    private long _id;

    private String userName;

    private LocalDate lastUpdated;
    
    private int totalCoins;
    private List<Integer> coinsPerDay;
    private List<Building> investmentBuildings;
    private List<Building> energySources;

}




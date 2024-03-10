package com.ecoquest.game.model;

import lombok.Data;

@Data
public class Building {
    private int plotNumber;
    private String buildingName;
    private int level;
    private int expectedEarnings;

}
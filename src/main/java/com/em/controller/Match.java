package com.em.controller;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class Match {
    private Team homeTeam;
    private Team awayTeam;
    private ArrayList<Integer> result;
    private boolean knockOutStages;
    private char victor;


    public Match(Team home, Team away, char victor, boolean knockout){
        homeTeam = home;
        awayTeam = away;
        knockOutStages = knockout;
        this.victor = victor;

    }

}

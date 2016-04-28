package com.em.controller;


/**
 * Created by frederiknygaard on 23.04.16.
 */
public class GroupMatch {
    private int homeGoals;
    private int awayGoals;
    private char HUB;
    private int matchNumber;

    public GroupMatch(int home, int away, int nr, char hub){
        homeGoals=home;
        awayGoals=away;
        HUB = hub;
        matchNumber = nr;
    }

    public String toString(){

        return "MatchNR:   " + matchNumber + "HomeGoals:   " + homeGoals + "Awaygoals:   " + awayGoals + "HUB:   " + HUB;
    }

}

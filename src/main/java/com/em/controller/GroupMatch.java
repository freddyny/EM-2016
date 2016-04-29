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

    public GroupMatch(){

    }
    public String toString(){

        return "MatchNR:   " + matchNumber + "HomeGoals:   " + homeGoals + "Awaygoals:   " + awayGoals + "HUB:   " + HUB;
    }

    public int getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(int homeGoals) {
        this.homeGoals = homeGoals;
    }

    public int getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(int awayGoals) {
        this.awayGoals = awayGoals;
    }

    public char getHUB() {
        return HUB;
    }

    public void setHUB(char HUB) {
        this.HUB = HUB;
    }

    public int getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(int matchNumber) {
        this.matchNumber = matchNumber;
    }
}

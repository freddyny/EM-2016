package com.em.controller;

/**
 * Created by frederiknygaard on 04.05.16.
 */
public abstract class Match {
    private int homeGoals;
    private int awayGoals;
    private char HUB;
    private int matchNumber;



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

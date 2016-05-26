package com.em.controller;

/**
 * Created by frederiknygaard on 04.05.16.
 */
public abstract class Match {
    private long homeGoals;
    private long awayGoals;
    private char HUB;
    private int matchNumber;



    public long getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(long homeGoals) {
        this.homeGoals = homeGoals;
    }

    public long getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(long awayGoals) {
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

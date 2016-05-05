package com.em.controller;

/**
 * Created by frederiknygaard on 04.05.16.
 */
public class KnockoutMatch extends Match {
    private String homeTeam;
    private String awayTeam;

    public KnockoutMatch(int home, int away, int nr, char hub, String homeTeam, String awayTeam) {
        setHomeGoals(home);
        setAwayGoals(away);
        setHUB(hub);
        setMatchNumber(nr);
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public KnockoutMatch() {

    }

    public String toString() {

        return "MatchNR:   " + getMatchNumber() + "  Hometeam:   " + homeTeam + "  Awayteam:  " + awayTeam + "HomeGoals:   " + getHomeGoals() + "   Awaygoals:   " + getAwayGoals() + "  HUB:   " + getHUB();
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }
}


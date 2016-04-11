package com.em.controller;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class Bet {
    private int homeScore;
    private int awayScore;
    private char victor;
    private Team homeTeam;
    private Team awayTeam;

    public Bet(int homeScore, int awayScore, char victor){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.victor = victor;

    }


    public Bet(int homeScore, int awayScore, char victor, Team homeTeam, Team awayTeam){
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.victor = victor;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

    }


    public int getHomeScore() {
        return homeScore;
    }



    public int getAwayScore() {
        return awayScore;
    }



    public char getVictor() {
        return victor;
    }


    public Team getHomeTeam() {
        return homeTeam;
    }



    public Team getAwayTeam() {
        return awayTeam;
    }

}

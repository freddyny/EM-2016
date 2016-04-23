package com.em.controller;


/**
 * Created by frederiknygaard on 23.04.16.
 */
public class GroupMatch {
    private int homeScore;
    private int awayScore;
    private char HUB;
    private int matchnr;

    public GroupMatch(int home, int away, int nr, char hub){
        homeScore=home;
        awayScore=away;
        HUB = hub;
        matchnr = nr;
    }

}

package com.em.controller;


/**
 * Created by frederiknygaard on 23.04.16.
 */
public class GroupMatch extends Match{


    public GroupMatch(long home, long away, int nr, char hub){
        setHomeGoals(home);
        setAwayGoals(away);
        setHUB(hub);
        setMatchNumber(nr);
    }

    public GroupMatch(){

    }
    public String toString(){

        return "MatchNR:   " + getMatchNumber() + "HomeGoals:   " +getHomeGoals() + "Awaygoals:   " +getAwayGoals()+ "HUB:   " + getHUB();
    }
}




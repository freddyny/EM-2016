package com.em.controller;

/**
 * Created by frederiknygaard on 19.05.16.
 */
public class Toppscorers {
    private int number;
    private String player;
    private long goals;


    public Toppscorers(){}

    public Toppscorers(int n, long g, String p){
        number= n;
        goals=g;
        player=p;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public long getGoals() {
        return goals;
    }

    public void setGoals(long goals) {
        this.goals = goals;
    }
}

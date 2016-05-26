package com.em.controller;


import java.util.ArrayList;

/**
 * Created by frederiknygaard on 19.05.16.
 */
public class Players {

    private ArrayList<Toppscorers> toppscorers;
    private ArrayList<BestPlayer> bestPlayer;


    public Players(){}

    public Players(ArrayList<Toppscorers> ts, ArrayList<BestPlayer> bp){
        toppscorers=ts;
        bestPlayer = bp;
    }

    public ArrayList<Toppscorers> getToppscorers() {
        return toppscorers;
    }

    public void setToppscorers(ArrayList<Toppscorers> toppscorers) {
        this.toppscorers = toppscorers;
    }

    public ArrayList<BestPlayer> getBestPlayer() {
        return bestPlayer;
    }

    public void setBestPlayer(ArrayList<BestPlayer> bestPlayers) {
        this.bestPlayer = bestPlayer;
    }
}

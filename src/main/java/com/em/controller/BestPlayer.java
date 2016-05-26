package com.em.controller;

/**
 * Created by frederiknygaard on 19.05.16.
 */
public class BestPlayer {
    private int number;
    private String player;

    public BestPlayer(){}
    public BestPlayer(int n, String p){
        number=n;
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
}

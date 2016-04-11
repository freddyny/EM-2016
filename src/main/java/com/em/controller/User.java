package com.em.controller;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class User {
    private String email;
    private String userName;
    private ArrayList<Bet> bets;
    private int score;

    public User(String userName, ArrayList<Bet> bets, String email){
        this.userName = userName;
        this.bets = bets;
        this.score = 0;
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public ArrayList<Bet> getBets() {
        return bets;
    }

    public void setBets(ArrayList<Bet> bets) {
        this.bets = bets;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int newPoints) {
        this.score = this.score + newPoints;

    }

    public String getEmail(){
        return email;
    }

}

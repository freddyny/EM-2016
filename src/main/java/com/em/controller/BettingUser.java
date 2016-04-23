package com.em.controller;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class BettingUser {
    private String email;
    private String userName;
    private int score;

    GroupStageBet groupA;
    GroupStageBet groupB;
    GroupStageBet groupC;
    GroupStageBet groupD;
    GroupStageBet groupE;
    GroupStageBet groupF;
    GroupStageBet groupG;
    GroupStageBet groupH;





    public BettingUser(){

        email = "frederikny@gmail.com";
        userName = "freddyny";
        this.score = 99;
    }


    public BettingUser(String userName, String email){
        this.userName = userName;
        this.score = 0;
        this.email = email;
    }

    public BettingUser(String email){
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString(){
        return "Username: " + userName + "\t" + "email: " + email + "\t" + "Score: " + score;
    }
}

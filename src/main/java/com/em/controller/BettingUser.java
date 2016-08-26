package com.em.controller;

import com.google.appengine.api.datastore.Entity;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class BettingUser {
    private String email;
    private String userName;
    private long score;
    private String id;




    public BettingUser(){

        email = "frederikny@gmail.com";
        userName = "freddyny";
        this.score = 99;
    }


    public BettingUser(Entity e){

        userName = (String) e.getProperty("userName");
        score = (long) e.getProperty("score");
        id = e.getKey().getName();
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


    public long getScore() {
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

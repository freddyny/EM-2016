package com.em.controller;

/**
 * Created by frederiknygaard on 10.04.16.
 */
public class Team {
    private String name;
    private boolean stillInIt;


    public Team(String name){
        this.setName(name);
        setStillInIt(true);

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStillInIt() {
        return stillInIt;
    }

    public void setStillInIt(boolean stillInIt) {
        this.stillInIt = stillInIt;
    }
}

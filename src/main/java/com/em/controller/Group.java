package com.em.controller;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 28.04.16.
 */
public class Group {

    private ArrayList<GroupMatch> matches;

    public String toString(){
        return "FØRSTE OBJEKT I LISTEN: " + matches.get(0);
    }

    public Group(ArrayList<GroupMatch> gm){
        matches = gm;
    }

    public Group(){

    }

    public ArrayList<GroupMatch> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<GroupMatch> matches) {
        this.matches = matches;
    }
}

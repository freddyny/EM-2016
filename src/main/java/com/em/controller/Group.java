package com.em.controller;

import java.util.ArrayList;

/**
 * Created by frederiknygaard on 28.04.16.
 */
public class Group {

    private ArrayList<GroupMatch> matches;
    private ArrayList<String> teams;
    private String name;

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

    public ArrayList<String> getTeams() {
        return teams;
    }

    public void setTeams(ArrayList<String> teams) {
        this.teams = teams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

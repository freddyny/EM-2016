package com.em.controller;


import java.util.ArrayList;

/**
 * Created by frederiknygaard on 19.05.16.
 */
public class UserBet {
    private ArrayList<GroupMatch> groupmatches;
    private ArrayList<KnockoutMatch> knockoutMatches;
    private String winner;
    private Players players;
    private ArrayList<ArrayList<String>> groupGuess;

    public UserBet() {
    }

    public UserBet(ArrayList<GroupMatch> g, ArrayList<KnockoutMatch> ko, String w, Players p, ArrayList<ArrayList<String>> gr){
        groupmatches=g;
        knockoutMatches=ko;
        winner=w;
        players = p;
        groupGuess = gr;
    }


    public ArrayList<GroupMatch> getGroups() {
        return groupmatches;
    }

    public void setGroups(ArrayList<GroupMatch> groups) {
        this.groupmatches = groups;
    }

    public ArrayList<KnockoutMatch> getKnockouts() {
        return knockoutMatches;
    }

    public void setKnockouts(ArrayList<KnockoutMatch> knockouts) {
        this.knockoutMatches = knockouts;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }

    public ArrayList<ArrayList<String>> getGroupGuess() {
        return groupGuess;
    }

    public void setGroupGuess(ArrayList<ArrayList<String>> groupGuess) {
        this.groupGuess = groupGuess;
    }
}

package com.em.controller;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by frederiknygaard on 04.05.16.
 */
public class Knockout {
    private ArrayList<KnockoutMatch> matches;

    public Knockout(ArrayList<KnockoutMatch> kn){
        matches = kn;
    }
    public Knockout(){

    }

    public ArrayList<KnockoutMatch> getMatches() {
        return matches;
    }

    public void setMatches(ArrayList<KnockoutMatch> matches) {
        this.matches = matches;
    }
}

package com.em.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by frederiknygaard on 28.04.16.
 */
public class DataStoreUtils {

    public static void putGroupInDatastore(String JSONGroup){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        Group group;
        Gson gson = new GsonBuilder().create();
        group = gson.fromJson(JSONGroup, Group.class);


        for(GroupMatch groupMatch: group.getMatches()){
            Entity match = new Entity("Match_"+groupMatch.getMatchNumber(), currentUser.getUserId());
            match.setProperty("HUB",""+groupMatch.getHUB());
            match.setProperty("homeGoals",groupMatch.getHomeGoals());
            match.setProperty("awayGoals",groupMatch.getAwayGoals());
            datastore.put(match);
        }

        Entity groupBet = new Entity("Group"+group.getName(), currentUser.getUserId());
        groupBet.setProperty("bet",group.getTeams());
        datastore.put(groupBet);


    }

    public static void putKnockoutInDatastore(String JSONGroup){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        Knockout knockout;
        Gson gson = new GsonBuilder().create();
        knockout = gson.fromJson(JSONGroup, Knockout.class);

        for(KnockoutMatch knockoutMatch: knockout.getMatches()){
            Entity match = new Entity("Match_"+knockoutMatch.getMatchNumber(), currentUser.getUserId());
            match.setProperty("HUB", "" + knockoutMatch.getHUB());
            match.setProperty("homeTeam", knockoutMatch.getHomeTeam());
            match.setProperty("awayTeam", knockoutMatch.getAwayTeam());

            match.setProperty("homeGoals", knockoutMatch.getHomeGoals());
            match.setProperty("awayGoals", knockoutMatch.getAwayGoals());

            datastore.put(match);
        }



    }




    public static void putUserInDataStore(String userName,int score){
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity user = new Entity("Users", currentUser.getUserId());
        user.setProperty("score", score);
        user.setProperty("userName", userName);

        datastore.put(user);

    }

    public static Entity getEntityFromDatastore(String tableName,String id) throws EntityNotFoundException {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        Entity datastoreEntity = datastore.get(KeyFactory.createKey(tableName, id));

        return datastoreEntity;
    }


    public static List<Entity> getAllUsersFromDatastore(String tableName){
        return getPreparedQuery(tableName,true,"score").asList(FetchOptions.Builder.withDefaults());

    }

    private static PreparedQuery getPreparedQuery(String kind,boolean sorted, String sortProperty){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        Query q;
        if(sorted){
            q = new Query(kind).addSort(sortProperty, Query.SortDirection.DESCENDING);;
        }
        else{
            q = new Query(kind);
        }
        return datastore.prepare(q);
    }

    public static String userDataStoreToJSON(String tableName){
        List<Entity> entities = getAllUsersFromDatastore(tableName);
        ArrayList<BettingUser> bettingUsers = new ArrayList<BettingUser>();
        for(Entity e:entities){
            BettingUser bu = new BettingUser(e);
            bettingUsers.add(bu);

        }
        Gson gson = new Gson();
        String json = gson.toJson(bettingUsers);
        return json;
    }
}

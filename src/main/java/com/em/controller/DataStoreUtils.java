package com.em.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

    public static void putUserInDataStore(String userName){
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity user = new Entity("Users", currentUser.getUserId());
        user.setProperty("score", 0);
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


}

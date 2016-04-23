package com.em.controller;

import com.google.appengine.api.datastore.*;

/**
 * Created by frederiknygaard on 19.04.16.
 */
public  class BettingUserManager {
    public static BettingUser getBettingUserFromDatastore(String userId){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        try {
            Entity user = datastore.get(KeyFactory.createKey("BettingUser", userId));
            BettingUser bu = (BettingUser) user.getProperty("bettingUser");
            return bu;
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

}

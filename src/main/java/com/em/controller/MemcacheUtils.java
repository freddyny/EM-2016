package com.em.controller;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.logging.Level;

/**
 * Created by frederiknygaard on 03.05.16.
 */
public class MemcacheUtils {
    private static final Log log = LogFactory.getLog(MemcacheUtils.class);

    private static final String SCORE_MEMCACHE_KEY = "SCORE";


    public static MemcacheService getMemcache(){
        MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
        syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
        return syncCache;
    }


    public static void putScores(){
        // Divide into chunks
        String json = DataStoreUtils.userDataStoreToJSON("Users");
        getMemcache().put(SCORE_MEMCACHE_KEY, json);

    }

    public static String getScores(){
        String scoresObject = (String) getMemcache().get(SCORE_MEMCACHE_KEY);
        if (scoresObject == null){
            log.info("Henter fra Database");
            String json = DataStoreUtils.userDataStoreToJSON("Users");
            getMemcache().put(SCORE_MEMCACHE_KEY,json);
            return json;
        }
        else{
            log.info("Henter fra Memcache");
            return scoresObject;
        }
    }

}

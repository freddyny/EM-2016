package com.em.controller;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.repackaged.com.google.gson.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by frederiknygaard on 28.04.16.
 */
public class DataStoreUtils {
    private static final Log log = LogFactory.getLog(DataStoreUtils.class);

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

    public static void completeToDataStore(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        List<Entity> entities = DataStoreUtils.getPreparedQuery("Users",false,"").asList(FetchOptions.Builder.withDefaults());
        for(Entity e:entities) {
            String userID = e.getKey().getName();

            Entity e_completeBet = getSingleEntity("CompleteBet", userID);
            String completeBetJson;
            try{
                completeBetJson = ((Text) e_completeBet.getProperty("Bet")).getValue();

            }catch (NullPointerException f){
                continue;
            }

            Gson gson = new GsonBuilder().create();
            UserBet userBet = gson.fromJson(completeBetJson, UserBet.class);



            for (int i = 0;i<36;i++){
                Entity groupMatch = getSingleEntity("Match_"+(i+1),userID);

                log.info("Match_"+(i+1)+userID);
                groupMatch.setProperty("HUB", ""+userBet.getGroups().get(i).getHUB());
                groupMatch.setProperty("homeGoals",userBet.getGroups().get(i).getHomeGoals());
                groupMatch.setProperty("awayGoals",userBet.getGroups().get(i).getAwayGoals());

                datastore.put(groupMatch);

            }
            int matchNr = 36;
            for (int i = 0;i<15;i++){
                Entity knockoutMatch = getSingleEntity("Match_"+(matchNr+1),userID);
                knockoutMatch.setProperty("HUB", ""+userBet.getKnockouts().get(i).getHUB());
                knockoutMatch.setProperty("homeGoals", userBet.getKnockouts().get(i).getHomeGoals());
                knockoutMatch.setProperty("awayGoals", userBet.getKnockouts().get(i).getAwayGoals());
                knockoutMatch.setProperty("awayTeam", userBet.getKnockouts().get(i).getAwayTeam());
                knockoutMatch.setProperty("homeTeam", userBet.getKnockouts().get(i).getHomeTeam());

                datastore.put(knockoutMatch);
                matchNr ++;
            }

            Entity winner = getSingleEntity("Winner", userID);
            winner.setProperty("Team", userBet.getWinner());
            datastore.put(winner);


            char[] groups = {'A','B','C','D','E','F'};
            int groupNr = 0;
            for(char c:groups){
                Entity group = getSingleEntity("Group"+c, userID);
                group.setProperty("bet",userBet.getGroupGuess().get(groupNr));

                datastore.put(group);
                groupNr++;

            }

        }


    }

    public static void putCompleteJsonInDS(){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        ArrayList<GroupMatch> groupMatches = new ArrayList<GroupMatch>();
        for (int i = 0;i<36;i++){
            Entity groupMatch = getSingleEntity("Match_"+(i+1),currentUser.getUserId());
            System.out.println("Group  "+i);
            String HUB = (String) groupMatch.getProperty("HUB");
            long homeGoals = (long) groupMatch.getProperty("homeGoals");
            long awayGoals = (long) groupMatch.getProperty("awayGoals");


            GroupMatch group = new GroupMatch(homeGoals,awayGoals,i+1,HUB.charAt(0));

            groupMatches.add(group);

        }


        ArrayList<KnockoutMatch> knockoutMatches = new ArrayList<KnockoutMatch>();
        for (int i = 36;i<51;i++){
            Entity groupMatch = getSingleEntity("Match_"+(i+1),currentUser.getUserId());
            System.out.println("KO: "+(i+1));

            String HUB = (String) groupMatch.getProperty("HUB");
            long homeGoals = (long) groupMatch.getProperty("homeGoals");
            long awayGoals = (long) groupMatch.getProperty("awayGoals");
            String homeTeam = (String) groupMatch.getProperty("homeTeam");

            String awayTeam = (String) groupMatch.getProperty("awayTeam");


            KnockoutMatch knockoutMatch = new KnockoutMatch(homeGoals,awayGoals,i,HUB.charAt(0),homeTeam,awayTeam);

            knockoutMatches.add(knockoutMatch);

        }

        Entity winner = getSingleEntity("Winner",currentUser.getUserId());
        String win = (String) winner.getProperty("Team");


        Entity playersEntity = getSingleEntity("Player",currentUser.getUserId());
        ArrayList<Toppscorers> toppscorers = new ArrayList<Toppscorers>();
        ArrayList<BestPlayer> bestPlayers = new ArrayList<BestPlayer>();

        for(int i = 0;i<3;i++){
            String toppscorer = (String) playersEntity.getProperty("Toppscorer"+(i+1));
            String bestPlayer = (String) playersEntity.getProperty("BestPlayer"+(i+1));
            long goals = (long) playersEntity.getProperty("Goal"+(i+1));

            Toppscorers ts = new Toppscorers(i+1,goals, toppscorer);
            toppscorers.add(ts);

            BestPlayer bp = new BestPlayer(i+1,bestPlayer);
            bestPlayers.add(bp);


        }

        Players players = new Players(toppscorers,bestPlayers);

        char[] groups = {'A','B','C','D','E','F'};
        ArrayList<ArrayList<String>> groupGuess = new ArrayList<ArrayList<String>>();
        for(char c:groups){

            Entity group = getSingleEntity("Group"+c,currentUser.getUserId());
            ArrayList<String> gr = (ArrayList<String>) group.getProperty("bet");

            groupGuess.add(gr);

        }

        UserBet userBet = new UserBet(groupMatches,knockoutMatches,win,players,groupGuess);

        Gson gson = new Gson();
        String jsonInString = gson.toJson(userBet);

        Entity betEntity = new Entity("CompleteBet", currentUser.getUserId());
        Text json = new Text(jsonInString);
        betEntity.setProperty("Bet", json);
        datastore.put(betEntity);


        Entity bet = getSingleEntity("CompleteBet",currentUser.getUserId());

        Text text = (Text) bet.getProperty("Bet");
        String string = text.getValue();
        System.out.println(string);

    }


    public static void DeletePlayer(String id){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        for (int i = 0;i<36;i++){
            datastore.delete(KeyFactory.createKey("Match_" + (i + 1), id));


        }

        ArrayList<KnockoutMatch> knockoutMatches = new ArrayList<KnockoutMatch>();
        for (int i = 36;i<51;i++){
            datastore.delete(KeyFactory.createKey("Match_" + (i + 1), id));

        }
        datastore.delete(KeyFactory.createKey("Winner", id));
        datastore.delete(KeyFactory.createKey("Player", id));

        char[] groups = {'A','B','C','D','E','F'};
        ArrayList<ArrayList<String>> groupGuess = new ArrayList<ArrayList<String>>();
        for(char c:groups){

            datastore.delete(KeyFactory.createKey("Group"+c, id));


        }
        datastore.delete(KeyFactory.createKey("CompleteBet", id));
        datastore.delete(KeyFactory.createKey("Users", id));


        log.info("SLETTET SPILLER");
    }


    public static Entity getSingleEntity(String kind, String id) {
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try {
            return datastore.get(KeyFactory.createKey(kind, id));
        } catch (EntityNotFoundException e) {
            return null;
        }
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


    public static void putPlayersInDatastore(String JSONPlayer){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        Players players;
        Gson gson = new GsonBuilder().create();
        players = gson.fromJson(JSONPlayer, Players.class);
        Entity player = new Entity("Player", currentUser.getUserId());
        System.out.println("\n\n"+players.getBestPlayer());
        for(Toppscorers ts: players.getToppscorers()){
            player.setProperty("Toppscorer"+ts.getNumber(), ts.getPlayer().replace(" ","").toLowerCase());
            player.setProperty("Goal" + ts.getNumber(), ts.getGoals());
        }

        for(BestPlayer bp: players.getBestPlayer()){
            player.setProperty("BestPlayer" + bp.getNumber(), bp.getPlayer().replace(" ", "").toLowerCase());

        }

        datastore.put(player);



    }

    public static void putWinnerInDatastore(String Winner){
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        Entity winner = new Entity("Winner", currentUser.getUserId());
        winner.setProperty("Team", Winner);

        datastore.put(winner);
    }




    public static void putUserInDataStore(long score,String id){
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity user = getSingleEntity("Users",id);
        user.setProperty("score", score);
        datastore.put(user);

    }


    public static void putUserInDataStoreWithUserName(String userName, long score){
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


        Entity user = new Entity("Users", currentUser.getUserId());
        user.setProperty("score", score);
        user.setProperty("userName",userName);

        datastore.put(user);

    }

    public static Entity getEntityFromDatastore(String tableName,String id) {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        try{
            Entity datastoreEntity = datastore.get(KeyFactory.createKey(tableName, id));
            return datastoreEntity;
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static List<Entity> getAllUsersFromDatastore(String tableName){
        return getPreparedQuery(tableName,true,"score").asList(FetchOptions.Builder.withDefaults());

    }




    public static PreparedQuery getPreparedQuery(String kind,boolean sorted, String sortProperty){
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

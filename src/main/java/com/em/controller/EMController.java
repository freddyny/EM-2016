package com.em.controller;

/**
 * Created by frederiknygaard on 08.04.16.
 */


import com.google.appengine.api.datastore.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Controller
public class EMController {

    private static final Log log = LogFactory.getLog(EMController.class);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/"));
        } else {
            return new ModelAndView("index", "user",currentUser.getEmail() );

        }

    }

    @RequestMapping(value = "/delPlayer", method = RequestMethod.GET)
    public ModelAndView del() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/delPlayer"));
        } else {
            return new ModelAndView("delete", "user",currentUser.getEmail() );

        }

    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView delete(@RequestParam("delete") String userId) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/"));
        } else {
            DataStoreUtils.DeletePlayer(userId);




            return new ModelAndView("index", "user",currentUser.getEmail() );

        }

    }




    @RequestMapping(value = "/matches", method = RequestMethod.GET)
    public ModelAndView matchOppsett() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/matches"));
        }
        else{
            String jsonString = getJSONFromFile("res/kampoppsett.json");
            ModelAndView mv = new ModelAndView("matches");

            mv.addObject("data", jsonString);
            return mv;
        }
    }


    @RequestMapping(value = "/rules", method = RequestMethod.GET)
    public ModelAndView rules() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/rules"));
        } else {
            return new ModelAndView("rules");

        }

    }


    @RequestMapping(value = "/scores", method = RequestMethod.GET)
    public ModelAndView scores() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/scores"));
        } else {
            String json = MemcacheUtils.getScores();
            ModelAndView mv = new ModelAndView("scores", "json",json);

            return mv;

        }

    }

    @RequestMapping(value = {"/game_{id}"}, method = RequestMethod.GET)
    public ModelAndView showTheme(@PathVariable(value= "id")String id) throws EntityNotFoundException {
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/scores"));
        } else {
            Entity bet = DataStoreUtils.getSingleEntity("CompleteBet", id);

            try{

                Text text = (Text) bet.getProperty("Bet");
                String string = text.getValue();
                String jsonString = getJSONFromFile("res/kampoppsett.json");

                ModelAndView mv =  new ModelAndView("completeBet", "json",string);
                mv.addObject("kampoppsett",jsonString);
                return mv;
            }
            catch (NullPointerException e) {
                return new ModelAndView("index", "json","Hei");
            }
        }



    }



    @RequestMapping(value = "/completeBet", method = RequestMethod.GET)
    public ModelAndView bet() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/completeBet"));
        } else {

            Entity bet = DataStoreUtils.getSingleEntity("CompleteBet", currentUser.getUserId());

            try{

                Text text = (Text) bet.getProperty("Bet");
                String string = text.getValue();
                String jsonString = getJSONFromFile("res/kampoppsett.json");

                ModelAndView mv =  new ModelAndView("completeBet", "json",string);
                mv.addObject("kampoppsett",jsonString);
                return mv;
            }
            catch (NullPointerException e) {
                return new ModelAndView("index", "json","Hei");
            }
        }

    }

    @RequestMapping(value = "/yourBetIkkeLov", method = RequestMethod.GET)
    public ModelAndView spill() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            ModelAndView mv = new ModelAndView("yourBet", "user",currentUser.getNickname() );
            return mv;


        }

    }



    @RequestMapping(value = "/groupA", method = RequestMethod.POST)
    public ModelAndView SendToGroupA(@RequestParam("userName") String userName) throws EntityNotFoundException {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {

            DataStoreUtils.putUserInDataStoreWithUserName(userName, 0);
            log.info("Skal legge til bruker i Datastore og oppdatere Memcache.");
            MemcacheUtils.putScores();


            String jsonString = getJSONFromFile("res/groupA.json");


            ModelAndView mv = new ModelAndView("group", "user",currentUser );
            mv.addObject("json",jsonString);
            mv.addObject("groupName","A");
            mv.addObject("nextGroupName", "B");
            return mv;

        }

    }





    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ModelAndView SendToGroupE(@RequestParam("group") String JSONGroup,
                                     @RequestParam("nextGroup")String thisGroup) throws ParseException {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            System.out.println("JSON: \t" + JSONGroup);
            DataStoreUtils.putGroupInDatastore(JSONGroup);
            String nextGroup = getNextGroup(thisGroup);
            if (thisGroup.equals("Done")){
                String newJsonString = getJSONFromFile("res/kampoppsett.json");
                System.out.println("ER I GROUP DONE");
                //NAVIGER TIL UTSLAGSRUNDER.
                String thisKnockout = "8-Delsfinale";
                String groups = getJSONFromFile("res/group.json");
                log.info("\n\n"+groups);
                ModelAndView mv = new ModelAndView("knockout", "user",currentUser );


                mv.addObject("json",newJsonString);
                mv.addObject("knockout",thisKnockout);
                mv.addObject("nextKnockout", getNextKnockout(thisKnockout));
                mv.addObject("groups",groups);
                mv.addObject("lastGuess","null");


                return mv;
            }
            else{
                String newJsonString = getJSONFromFile("res/group" + thisGroup + ".json");
                ModelAndView mv = new ModelAndView("group", "user",currentUser );
                mv.addObject("json",newJsonString);
                mv.addObject("groupName",thisGroup);
                mv.addObject("nextGroupName",nextGroup);
                return mv;

            }

        }
    }
    @RequestMapping(value = "/knockout", method = RequestMethod.POST)
    public ModelAndView knockOut(@RequestParam("group")String JSONKnockout,
                                 @RequestParam("nextKnockout")String thisKnockout) throws EntityNotFoundException {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {

            if (thisKnockout.equals("Vinner")){
                DataStoreUtils.putKnockoutInDatastore(JSONKnockout);

                ModelAndView mv = new ModelAndView("vinner", "user",currentUser );
                String newJsonString = getJSONFromFile("res/kampoppsett.json");
                mv.addObject("json",newJsonString);
                return mv;
            }
            else{
                log.info("\n\n\n"+JSONKnockout);

                DataStoreUtils.putKnockoutInDatastore(JSONKnockout);
                String nextKnockout = getNextKnockout(thisKnockout);


                String newJsonString = getJSONFromFile("res/kampoppsett.json");
                ModelAndView mv = new ModelAndView("knockout", "user",currentUser );
                mv.addObject("json",newJsonString);
                mv.addObject("knockout", thisKnockout);
                mv.addObject("nextKnockout", nextKnockout);
                mv.addObject("lastGuess",JSONKnockout);
                mv.addObject("groups","null");
                return mv;
            }
        }

    }

    @RequestMapping(value = "/players", method = RequestMethod.POST)
    public ModelAndView ToppScorer(@RequestParam("winner")String winner) throws EntityNotFoundException {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            DataStoreUtils.putWinnerInDatastore(winner);



            ModelAndView mv = new ModelAndView("players");
            return mv;
        }

    }

    @RequestMapping(value = "/finished", method = RequestMethod.POST)
    public ModelAndView finished(@RequestParam("players") String json){
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            System.out.println("\n\n" + json + "\n\n");
            DataStoreUtils.putPlayersInDatastore(json);
            DataStoreUtils.putCompleteJsonInDS();

            Entity betEntity = DataStoreUtils.getSingleEntity("CompleteBet", currentUser.getUserId());

            Text betText = (Text) betEntity.getProperty("Bet");
            String betString = betText.getValue();

            sendMail(currentUser.getEmail(), currentUser.getNickname(), "Em-konkurranse-svar fra: " + currentUser.getEmail(), betString);

            ModelAndView mv = new ModelAndView("finished", "user",currentUser.getNickname() );
            return mv;
        }

    }


    @RequestMapping(value = "/addMatchResult", method = RequestMethod.GET)
        public ModelAndView addResult() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {

            ModelAndView mv = new ModelAndView("addMatchResult", "command", new FormTest());
            mv.addObject("msg","Legg til resultat her");
            return mv;



        }
    }

    @RequestMapping(value = "/addMatchResultPost", method = RequestMethod.POST)
    public ModelAndView addResultPOST(@RequestParam("matchNr") int matchNr,@RequestParam("result") String result,@RequestParam("HUB") String hub) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            long homeGoals = Long.parseLong("" + result.charAt(0));
            long awayGoals = Long.parseLong("" + result.charAt(2));

            int score = 0;

            List<Entity> entities = DataStoreUtils.getPreparedQuery("Match_"+matchNr,false,"").asList(FetchOptions.Builder.withDefaults());
            for(Entity e:entities) {
                score = 0;
                String userID = e.getKey().getName();
                //HUB	awayGoals	homeGoals
                long homeGoalGuess = (long) e.getProperty("homeGoals");
                long awayGoalGuess = (long) e.getProperty("awayGoals");
                String hubGuess = (String) e.getProperty("HUB");

                log.info("\nhomeGoal: "+ homeGoalGuess + " VS " +homeGoals);
                log.info("\nawayGoal: "+ awayGoalGuess + " VS " +awayGoals);
                log.info("\nHUB: "+ hubGuess + " VS " +hub);

                if ((homeGoals == homeGoalGuess) &&(awayGoals==awayGoalGuess)){
                    score+=2;
                }
                if (hub.replace(" ","").equals(hubGuess.replace(" ",""))) {
                    score += 1;
                }

                log.info("\n SCORE:  "+score);
                if(score != 0){
                    Entity userEntity = DataStoreUtils.getEntityFromDatastore("Users",userID);
                    long userScore = (long) userEntity.getProperty("score");
                    userScore += score;
                    userEntity.setProperty("score",userScore);
                    datastore.put(userEntity);
                }

            }

            MemcacheUtils.putScores();

            sendMail("frederikny@gmail.com", "nygis", "[EM-Bones] KnockoutResults matchNr: +" + matchNr, "Du la til resultat i en knockoutmatch:\nMatchnr " + matchNr + " \nResultat: " + result + "\nHUB: " + hub);
            ModelAndView mv = new ModelAndView("addMatchResult", "command", new FormTest());
            mv.addObject("msg","Du har suksessfullt lagt til et resultat i kampnr: " + matchNr + " med resultat: " + result + " og HUB: " + hub);

            return mv;

        }
    }


    @RequestMapping(value = "/addKnockoutMatchResult", method = RequestMethod.GET)
    public ModelAndView addKnockoutResult() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {


            ModelAndView mv = new ModelAndView("addKnockoutMatchResult", "command", new FormTest());
            mv.addObject("msg", "Legg til resultat her");
            return mv;



        }
    }
    @RequestMapping(value = "/addKnockoutMatchResultPost", method = RequestMethod.POST)
    public ModelAndView addKnockoutResultPOST(@RequestParam("matchNr") int matchNr, @RequestParam("result") String result,
                                              @RequestParam("HUB") String hub, @RequestParam("homeTeam") String homeTeam,
                                              @RequestParam("awayTeam") String awayTeam, @RequestParam("knockoutType") String knockoutType) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        int resultScore = 4;
        int HUBScore = 2;
        int teamScore = 0;
        hub = hub.toUpperCase();
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            long homeGoals = Long.parseLong("" + result.charAt(0));
            long awayGoals = Long.parseLong("" + result.charAt(2));

            switch(knockoutType){
                case "8-Delsfinale":
                    teamScore = 3;
                    break;

                case "Kvartfinale":
                    teamScore = 4;
                    break;
                case "Semifinale":
                    teamScore = 5;
                    break;
                case "Finale":
                    teamScore = 10;
                    break;

            }
            int score;
            List<Entity> entities = DataStoreUtils.getPreparedQuery("Match_"+matchNr,false, "").asList(FetchOptions.Builder.withDefaults());
            for(Entity e:entities) {
                score = 0;
                String userID = e.getKey().getName();
                //HUB	awayGoals	homeGoals
                long homeGoalGuess = (long) e.getProperty("homeGoals");
                long awayGoalGuess = (long) e.getProperty("awayGoals");
                String hubGuess = (String) e.getProperty("HUB");
                String homeTeamGuess = (String) e.getProperty("homeTeam");
                String awayTeamGuess = (String) e.getProperty("awayTeam");

                /*log.info("\n\nhomeGoal: "+ homeGoalGuess + " VS " +homeGoals);
                log.info("\n\nawayGoal: "+ awayGoalGuess + " VS " +awayGoals);
                log.info("\n\nHUB: "+ hubGuess + " VS " +hub);
                log.info("\n\nHomeTeam: " + homeTeam + " VS " + homeTeamGuess);
                log.info("\n\nAwayTeam: " + awayTeam + " VS " + awayTeamGuess);
                log.info("\n\nKnockout: " + knockoutType);*/

                if(homeTeam.equals(homeTeamGuess) &&awayTeam.equals(awayTeamGuess)){

                    if ((homeGoals == homeGoalGuess) &&(awayGoals==awayGoalGuess)){
                        score+=resultScore;
                    }

                    if (hub.replace(" ","").equals(hubGuess.replace(" ", ""))) {
                        score += HUBScore;
                    }
                }

                if (homeTeam.equals(homeTeamGuess)){
                    score += teamScore;
                }

                if (awayTeam.equals(awayTeamGuess)){
                    score += teamScore;
                }

                log.info("\n SCORE:  "+score);
                if(score != 0){
                    Entity userEntity = DataStoreUtils.getEntityFromDatastore("Users",userID);
                    long userScore = (long) userEntity.getProperty("score");
                    userScore += score;
                    userEntity.setProperty("score",userScore);
                    datastore.put(userEntity);
                }

            }

            MemcacheUtils.putScores();
            sendMail("frederikny@gmail.com", "nygis", "[EM-Bones] KnockoutResults matchNr: +" + matchNr, "Du la til resultat i en knockoutmatch:\nMatchnr " + matchNr + " \nResultat: " + result + "\nHUB: " + hub + "\nHometeam: " + homeTeam + "\nAwayteam: " + awayTeam + "\nKnockoutType: " + knockoutType);
            ModelAndView mv = new ModelAndView("addKnockoutMatchResult", "command", new FormTest());
            mv.addObject("msg", "Du la til resultat i en knockoutmatch:\nMatchnr " + matchNr + " \nResultat: " + result + "\nHUB: " + hub
                    + "\nHometeam: " + homeTeam + "\nAwayteam: " + awayTeam + "\nKnockoutType: " + knockoutType);

            return mv;

        }
    }

    @RequestMapping(value = "/addGroups", method = RequestMethod.GET)
    public ModelAndView addGroup() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            ModelAndView mv = new ModelAndView("addGroups", "command", new FormTest());
            mv.addObject("msg","Legg til resultat her");
            return mv;
        }
    }


    @RequestMapping(value = "/addGroupsPOST", method = RequestMethod.POST)
    public ModelAndView addKnockoutResultPOST(@RequestParam("group") String group, @RequestParam("firstPlace") String firstPlace,
                                              @RequestParam("secondPlace") String secondPlace, @RequestParam("thirdPlace") String thirdPlace,
                                              @RequestParam("forthPlace") String forthPlace) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        ArrayList<String> teams = new ArrayList<String>();

        teams.add(firstPlace);
        teams.add(secondPlace);
        teams.add(thirdPlace);
        teams.add(forthPlace);

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            List<Entity> entities = DataStoreUtils.getPreparedQuery("Group"+group,false, "").asList(FetchOptions.Builder.withDefaults());

            for(Entity e:entities) {
                int i = 0;
                int score = 0;
                String userID = e.getKey().getName();
                ArrayList<String> guessedTeam = (ArrayList<String>) e.getProperty("bet");
                for(String teamGuess: guessedTeam){

                    log.info(teamGuess + " VS " + teams.get(i));

                    if(teamGuess.equals(teams.get(i))){
                        score +=2;

                    }
                    i++;

                }
                if(score != 0){
                    Entity userEntity = DataStoreUtils.getEntityFromDatastore("Users",userID);
                    long userScore = (long) userEntity.getProperty("score");
                    userScore += score;
                    userEntity.setProperty("score",userScore);
                    datastore.put(userEntity);
                }

            }

            MemcacheUtils.putScores();
            sendMail("frederikny@gmail.com", "nygis", "[EM-Bones] GroupResult for gruppe " + group, "Du la til disse resultatene for gruppen:\n1. " + firstPlace + " \n2. : " + secondPlace + "\n3. : " + thirdPlace + "\n4. : " + forthPlace);
            ModelAndView mv = new ModelAndView("addGroups", "command", new FormTest());
            mv.addObject("msg", "[EM-Bones] GroupResult for gruppe " + group+ "\nDu la til disse resultatene for gruppen:\n1. " + firstPlace + " \n2. : " + secondPlace + "\n3. : " + thirdPlace + "\n4. : " + forthPlace);

            return mv;

        }
    }


    @RequestMapping(value = "/addPlayerScores", method = RequestMethod.GET)
    public ModelAndView addPlayerScores() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            ModelAndView mv = new ModelAndView("addPlayers", "command", new FormTest());
            mv.addObject("msg","Legg til resultat her");
            return mv;
        }
    }


    @RequestMapping(value = "/addPlayerScoresPOST", method = RequestMethod.POST)
    public ModelAndView addPlayerScoresPOST(@RequestParam("toppscorer1") String toppscorer1, @RequestParam("toppscorer2") String toppscorer2,
                                              @RequestParam("toppscorer3") String toppscorer3, @RequestParam("goals1") int goal1,
                                              @RequestParam("goals2") int goal2,@RequestParam("goals3") int goal3,
                                              @RequestParam("bestPlayer1") String bestPlayer,@RequestParam("bestPlayer2") String winner) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        ArrayList<String> toppscorers = new ArrayList<String>();
        ArrayList<Integer> goals = new ArrayList<>();

        goals.add(goal1);
        goals.add(goal2);
        goals.add(goal3);

        toppscorers.add(toppscorer1.replace(" ","").toLowerCase());
        toppscorers.add(toppscorer2.replace(" ","").toLowerCase());
        toppscorers.add(toppscorer3.replace(" ","").toLowerCase());
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addMatchResult"));
        } else {
            List<Entity> entities = DataStoreUtils.getPreparedQuery("Player", false, "").asList(FetchOptions.Builder.withDefaults());

            for(Entity e:entities) {
                int i = 0;
                int score = 0;
                String userID = e.getKey().getName();
                ArrayList<String> guessedToppscorer = new ArrayList<>();
                ArrayList<Long> guessedGoals = new ArrayList<>();
                ArrayList<String> guessedBestPLayer = new ArrayList<>();

                guessedToppscorer.add(((String) e.getProperty("Toppscorer1")).replace(" ","").toLowerCase());
                guessedToppscorer.add(((String) e.getProperty("Toppscorer2")).replace(" ","").toLowerCase());
                guessedToppscorer.add(((String) e.getProperty("Toppscorer3")).replace(" ","").toLowerCase());

                guessedGoals.add((long) e.getProperty("Goal1"));
                guessedGoals.add((long) e.getProperty("Goal2"));
                guessedGoals.add((long) e.getProperty("Goal3"));

                guessedBestPLayer.add(((String) e.getProperty("BestPlayer1")).replace(" ","").toLowerCase());
                guessedBestPLayer.add(((String) e.getProperty("BestPlayer2")).replace(" ","").toLowerCase());
                guessedBestPLayer.add(((String) e.getProperty("BestPlayer3")).replace(" ","").toLowerCase());




                for (int j = 0; j<3;j++){
                    if(guessedToppscorer.get(j).equals(toppscorers.get(j))){
                        if( guessedGoals.get(j).intValue() == goals.get(j)){
                            score += 15;
                        }
                        else{
                            score += 10;
                        }
                    }
                    else{
                        if(toppscorers.contains(guessedToppscorer.get(j))){
                            for (int x = 0; x<3;x++){
                                if(guessedToppscorer.get(j).equals(toppscorers.get(x))){
                                    if (guessedGoals.get(j).intValue() == goals.get(x)){
                                        score +=10;
                                    }
                                    else {score+=5;}
                                }
                            }
                        }
                    }

                    if(guessedBestPLayer.get(j).equals(bestPlayer.replace(" ","").toLowerCase())){
                        switch(j){
                            case 0:
                                score += 20;
                                break;
                            case 1:
                                score += 10;
                                break;
                            case 2:
                                score +=5;
                                break;
                        }
                    }
                }

                Entity winnerEntity = DataStoreUtils.getSingleEntity("Winner",userID);
                String guessedWinner =(String) winnerEntity.getProperty("Team");

                if(guessedWinner.equals(winner)) score +=20;

                log.info(score);
                if(score != 0){
                    Entity userEntity = DataStoreUtils.getEntityFromDatastore("Users",userID);
                    long userScore = (long) userEntity.getProperty("score");
                    userScore += score;
                    userEntity.setProperty("score",userScore);
                    datastore.put(userEntity);
                }
                i++;

            }



            MemcacheUtils.putScores();
            sendMail("frederikny@gmail.com", "nygis", "[EM-Bones] Player score", "Toppscorer 1: " + toppscorer1 + " With goals " + goal1 + "Toppscorer 2: " + toppscorer2 + " With goals " + goal2 + "Toppscorer 3: " + toppscorer3 + " With goals " + goal3 + " BEst Player: " + bestPlayer + " Winner: "+ winner);
            ModelAndView mv = new ModelAndView("addPlayers", "command", new FormTest());
            mv.addObject("msg", "[EM-Bones] "+ "Toppscorer 1: "+ toppscorer1 + " With goals "+ goal1 + "Toppscorer 2: "+ toppscorer2 + " With goals "+ goal2 + "Toppscorer 3: "+ toppscorer3 + " With goals "+ goal3 + " BEst Player: " + bestPlayer + " Winner: "+ winner);

            return mv;

        }
    }




    @RequestMapping(value = "/addUserIkkeLov", method = RequestMethod.GET)
    public ModelAndView addUser() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addUser"));
        } else {
            log.info(" LOOL. " + currentUser.getEmail());
            ModelAndView mv = new ModelAndView("addUser", "command", new FormTest());

            String jsonString = getJSONFromFile("res/data.json");
            mv.addObject("data", jsonString);
            mv.addObject("groupName", "A");

            return mv;



        }
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public ModelAndView addUserPost(BindingResult result,
                                    ModelMap model,
                                    HttpServletRequest request) {
        if (result.hasErrors()) {
            return new ModelAndView();
        }
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addUser"));
        } else {
            log.info(" LOOL. " + currentUser.getEmail());
            return new ModelAndView("test", "newUser",currentUser.getEmail() );
        }
    }
    @RequestMapping(value = "/completeBetToDatastore", method = RequestMethod.GET)
    public ModelAndView completeBetToDatastore() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/index"));
        } else {
            DataStoreUtils.completeToDataStore();
            log.info("FERDIG Å LEGGE TIL INFO");

            ModelAndView mv = new ModelAndView("/index", "user",currentUser.getNickname() );
            return mv;
        }
    }


    @RequestMapping(value = "/addScore", method = RequestMethod.GET)
         public ModelAndView scoreADd() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            ModelAndView mv = new ModelAndView("addScore", "user",currentUser.getNickname() );
            return mv;
        }
    }

    @RequestMapping(value = "/addScore", method = RequestMethod.POST)
    public ModelAndView scoreADdPost(@RequestParam("id") String id, @RequestParam("score") int score) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            Entity userEntity = DataStoreUtils.getEntityFromDatastore("Users",id);
            long userScore = (long) userEntity.getProperty("score");

            long newScore = userScore + score;

            DataStoreUtils.putUserInDataStore(newScore,id);
            MemcacheUtils.putScores();

            ModelAndView mv = new ModelAndView("index", "user",currentUser.getNickname() );
            return mv;
        }

    }



    private String getJSONFromFile(String file){
        try {
            JSONParser parser = new JSONParser();
            FileReader fileReader = new FileReader(file);
            JSONObject json = (JSONObject) parser.parse(fileReader);


            Gson gson = new GsonBuilder().setPrettyPrinting().create();

            String jsonString = gson.toJson(json);



            return jsonString;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    return null;
    }


    private String getNextGroup(String thisGrou){
        switch(thisGrou){
            case "A":
                return "B";
            case "B":
                return "C";
            case "C":
                return "D";
            case "D":
                return "E";
            case "E":
                return "F";
            case "F":
                return "Done";
            default:
                return "";

        }

    }

    private String getNextKnockout(String thisKnockOut){
        switch(thisKnockOut){
            case "8-Delsfinale":
                return "Kvartfinale";
            case "Kvartfinale":
                return "Semifinale";
            case "Semifinale":
                return "Finale";
            case "Finale":
                return "Vinner";
            case "Vinner":
                return "Ferdig";
            default:
                return "";

        }

    }
    private void sendMail(String from, String userName,String sub, String bet){
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(from, userName));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress("frederikny@gmail.com", "Frederik Nygaard"));
            msg.setSubject(sub );
            msg.setText(bet);
            Transport.send(msg);
        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
            // ...
        }

    }




    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public ModelAndView test() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/"));
        } else {
            return new ModelAndView("test", "user",currentUser.getEmail() );

        }

    }

}



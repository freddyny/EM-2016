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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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
                    + userService.createLoginURL("/"));
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


    @RequestMapping(value = "/completeBet", method = RequestMethod.GET)
    public ModelAndView bet() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();


        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/"));
        } else {

            Entity bet = DataStoreUtils.getSingleEntity("CompleteBet",currentUser.getUserId());

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

    @RequestMapping(value = "/matches", method = RequestMethod.GET)
    public ModelAndView matchOppsett() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addUser"));
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
                    + userService.createLoginURL("/"));
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
                    + userService.createLoginURL("/"));
        } else {
            String json = MemcacheUtils.getScores();
            ModelAndView mv = new ModelAndView("scores", "json",json);

            return mv;

        }

    }


    @RequestMapping(value = "/yourBet", method = RequestMethod.GET)
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

            DataStoreUtils.putUserInDataStore(userName, 0);
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
                ModelAndView mv = new ModelAndView("knockout", "user",currentUser );
                mv.addObject("json",newJsonString);
                mv.addObject("knockout",thisKnockout);
                mv.addObject("nextKnockout", getNextKnockout(thisKnockout));

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
                    //Legg inn i DATASTORE.
                System.out.println(JSONKnockout);

                DataStoreUtils.putKnockoutInDatastore(JSONKnockout);
                String nextKnockout = getNextKnockout(thisKnockout);


                String newJsonString = getJSONFromFile("res/kampoppsett.json");
                ModelAndView mv = new ModelAndView("knockout", "user",currentUser );
                mv.addObject("json",newJsonString);
                mv.addObject("knockout", thisKnockout);
                mv.addObject("nextKnockout", nextKnockout);
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





    @RequestMapping(value = "/addUser", method = RequestMethod.GET)
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
                mv.addObject("data",jsonString);
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
        }
        else{
            log.info(" LOOL. " + currentUser.getEmail());
            return new ModelAndView("test", "newUser",currentUser.getEmail() );
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
    public ModelAndView scoreADdPost(@RequestParam("userName") String userName, @RequestParam("score") int score) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            DataStoreUtils.putUserInDataStore(userName,score);
            MemcacheUtils.putScores();

            ModelAndView mv = new ModelAndView("index", "user",currentUser.getNickname() );
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
            System.out.println("\n\n"+json+"\n\n");
            DataStoreUtils.putPlayersInDatastore(json);
            DataStoreUtils.putCompleteJsonInDS();



            //IKKE RETURNERE INDEX
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
}

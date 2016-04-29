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
import javax.xml.crypto.Data;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


@Controller
public class EMController {

    private static final Log log = LogFactory.getLog(EMController.class);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();


    @RequestMapping("/test")
    public ModelAndView test() {
        log.info(" LOOL. " );
        return new ModelAndView("test", "welcomeMsg", "Hello World, " );
    }

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

    @RequestMapping(value = "/yourBet", method = RequestMethod.GET)
    public ModelAndView spill() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            ModelAndView mv = new ModelAndView("yourBet", "user",currentUser );
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

            DataStoreUtils.putUserInDataStore(userName);

            Entity datastoreUser =  DataStoreUtils.getEntityFromDatastore("Users", currentUser.getUserId());

            String userNameD = (String) datastoreUser.getProperty("userName");
            long score = (long) datastoreUser.getProperty("score");

            System.out.print("Username: \t"+ userNameD + "\tScore: \t" + score);

            String jsonString = getJSONFromFile("res/groupA.json");


            ModelAndView mv = new ModelAndView("group", "user",currentUser );
            mv.addObject("json",jsonString);
            mv.addObject("groupName","A");
            mv.addObject("nextGroupName","B");
            return mv;

        }

    }





    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public ModelAndView SendToGroupE(@RequestParam("group") String JSONGroup,
                                     @RequestParam("nextGroup")String thisGroup) throws ParseException {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        System.out.println("NEXT GROUP:    \n"+thisGroup+"\n");
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            System.out.println("JSON: \t"+JSONGroup);
            DataStoreUtils.putGroupInDatastore(JSONGroup);
            String newJsonString = getJSONFromFile("res/group" + thisGroup + ".json");

            ModelAndView mv = new ModelAndView("group", "user",currentUser );
            mv.addObject("json",newJsonString);
            mv.addObject("groupName",thisGroup);
            mv.addObject("nextGroupName",getNextGroup(thisGroup));
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
                mv.addObject("groupName","A");

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
}

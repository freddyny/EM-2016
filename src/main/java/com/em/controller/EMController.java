package com.em.controller;

/**
 * Created by frederiknygaard on 08.04.16.
 */


import com.google.appengine.api.datastore.*;
import com.google.appengine.repackaged.com.google.datastore.v1.Filter;
import com.google.appengine.repackaged.com.google.gson.JsonArray;
import com.google.appengine.repackaged.com.google.gson.JsonObject;
import com.google.appengine.repackaged.com.google.gson.JsonParser;
import com.google.appengine.repackaged.com.google.gson.stream.JsonReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import java.io.InputStream;
import java.util.ArrayList;

@Controller
public class EMController {

    private static final Log log = LogFactory.getLog(EMController.class);



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
    public ModelAndView SendToGroupA(@RequestParam("userName") String userName) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {

            BettingUser bettingUser = new BettingUser(userName, currentUser.getEmail());
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

            ///TODO: KOM HIT: FÅR BESKJED OM AT JEG IKKE KAN BRUKE BETTINGUSER SOM ENTIITET. SE HVA VI GJORDE MED THEME.
            ///TODO: LAGRE EN ENTITET FOR HVER KAMP. EN ENTITET FOR HVER GRUPPE(TIL Å TIPPE GRUPPESPILLPOS). FINN UT HVA VI GJØR MED GRUPPESPILL
            ///TODO: START MED Å LAGE EN SIDE HVOR VI LAGER ALLE KAMPENE I DATASTORE MED EN KEY ENKELT Å AKSESSERE.


            String jsonString = getJSONFromFile("res/groupA.json");


            ModelAndView mv = new ModelAndView("group", "user",currentUser );
            mv.addObject("json",jsonString);
            mv.addObject("groupName","A");
            mv.addObject("nextGroupName","B");


            System.out.print("HEYHEYHEY\n\n");
            return mv;

        }

    }
    @RequestMapping(value = "/groupB", method = RequestMethod.POST)
    public ModelAndView SendToGroupB(@RequestParam("group") String JSONGroup) {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/yourBet"));
        } else {
            //JsonParser parser = new JsonParser();
            //JsonObject rootObj = parser.parse(JSONGroup).getAsJsonObject();

            System.out.print("ER I POST GROUP B: \n\n" + JSONGroup);

            //Entity entityUser = new Entity("bettingUser",currentUser.getUserId());

            ///TODO: HAR FÅTT SENDT INFO TIL DETTE STEDET: BRUK GSON TIL Å FIKSE--> LEGG TIL I DATASTORE (Y) WOOP!
            ///TODO: LAGE KLASSE FOR GRUPPE OG GRUPPESPILL-KAMP

            String jsonString = getJSONFromFile("res/groupA.json");


            ModelAndView mv = new ModelAndView("group", "user",currentUser );
            mv.addObject("json",jsonString);
            mv.addObject("groupName","B");



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


}

package com.em.controller;

/**
 * Created by frederiknygaard on 08.04.16.
 */


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@Controller
public class EMController {

    private static final Log log = LogFactory.getLog(GuestbookController.class);


    @RequestMapping("/test")
    public ModelAndView test() {
        log.info(" LOOL. " );
        return new ModelAndView("test", "welcomeMsg", "Hello World, " );
    }

    @RequestMapping("/addUser")
    public ModelAndView addUser() {
        //Login
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            return new ModelAndView("redirect:"
                    + userService.createLoginURL("/addUser"));
        }
        else{
            log.info(" LOOL. " + currentUser.getEmail());
            return new ModelAndView("addUser", "userName", currentUser.getEmail());
        }



    }


}

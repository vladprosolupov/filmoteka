package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import web.model.ClientJSON;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    private static final Logger log = LogManager.getLogger(RootWebController.class);

    @RequestMapping(value = "/")
    public String index() {
        log.info("index()");

        return "index";
    }

    @RequestMapping("/index")
    public RedirectView localRedirect() {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080");
        return redirectView;
    }

    @RequestMapping(value = "/login")
    public String login() {
        log.info("login()");

        return "login";
    }

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        ModelAndView model = new ModelAndView("register");
        model.addObject("client", new ClientJSON());
        return model;
    }

    @RequestMapping(value = "/403")
    public String error() {
        log.info("error()");

        return "403";
    }


}

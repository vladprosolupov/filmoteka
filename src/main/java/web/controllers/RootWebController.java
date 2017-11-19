package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import web.model.ClientJSON;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    private static final Logger log = LogManager.getLogger(RootWebController.class);

    @RequestMapping(value = "/")
    public String index() {
        log.info("index()");

        log.info("index() returns : index");
        return "index";
    }

    @RequestMapping("/index")
    public RedirectView localRedirect() {
        log.info("localRedirect()");

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://localhost:8080");

        log.info("localRedirect() returns : localhost:8080");
        return redirectView;
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) {
        log.info("login(request=" + request + ")");

        String referrer = request.getHeader("Referer");

        if (referrer != null) {
            log.info("referrer is not null");

            request.getSession().setAttribute("url_prior_login", referrer);

            log.info("end of if");
        }

        log.info("login() returns : login");
        return "login";
    }

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        log.info("register()");

        ModelAndView model = new ModelAndView("register");
        model.addObject("client", new ClientJSON());

        log.info("register returns : register");
        return model;
    }

    @RequestMapping(value = "/403")
    public String error() {
        log.info("error()");

        log.info("error() returns : 403");
        return "403";
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String pageNew(){
        log.info("pageNew()");
        log.info("pageNew() returns : new");
        return "new";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        log.info("profile()");
        log.info("profile() returns : profile");
        return "profile";
    }


}

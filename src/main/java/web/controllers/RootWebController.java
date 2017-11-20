package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import java.util.Enumeration;
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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.info("login() returns : login");
            return "login";
        }else {
            log.info("client already logged in: login() returns index");
            return "redirect:/index";
        }


    }

    @RequestMapping(value = "/register")
    public ModelAndView register(HttpServletRequest request) {
        log.info("register()");

        ModelAndView model = new ModelAndView("register");
        model.addObject("client", new ClientJSON());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.info("register returns : register");
            return model;
        }else {
            log.info("client already registered: register() returns index");
            return new ModelAndView("redirect:/index");
        }
    }

    @RequestMapping(value = "/403")
    public String error403(HttpServletRequest request) {
        log.info("error403()");

        String referrer = request.getHeader("Referer");

        if(referrer != null){
            log.info("error403(): redirecting to 403");
            return "redirect:/403";
        }
        log.info("error403() returns : 403");
        return "403";
    }

    @RequestMapping(value = "/404")
    public String error404(HttpServletRequest request) {
        log.info("error404()");

        String referrer = request.getHeader("Referer");

        if(referrer != null){
            log.info("error404(): redirecting to 404");
            return "redirect:/404";
        }
        log.info("error404() returns : 404");
        return "404";
    }

    @RequestMapping(value = "/500")
    public String error500(HttpServletRequest request) {
        log.info("error500()");

        String referrer = request.getHeader("Referer");

        if(referrer != null){
            log.info("error500(): redirecting to 500");
            return "redirect:/500";
        }
        log.info("error500() returns : 500");
        return "500";
    }

    @RequestMapping(value = "/best", method = RequestMethod.GET)
    public String best(){
        log.info("best()");
        log.info("best() returns : best");
        return "best";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        log.info("profile()");
        log.info("profile() returns : profile");
        return "profile";
    }


}

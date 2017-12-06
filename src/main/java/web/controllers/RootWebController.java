package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import web.dao.ClientDb;
import web.dao.VerificationTokenDb;
import web.model.ClientJSON;
import web.services.ClientService;
import web.services.VerificationTokenService;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Rostyk on 12.06.2017.
 */
@Controller
public class RootWebController {

    @Autowired
    private VerificationTokenService tokenService;

    @Autowired
    private ClientService clientService;

    private static final Logger log = LogManager.getLogger(RootWebController.class);

    @RequestMapping(value = "/")
    public String index() {
        log.info("index()");

        log.info("index() returns : index, ");
        return "index";
    }

    @RequestMapping("/index")
    public RedirectView localRedirect(HttpServletRequest request) {
        log.info("localRedirect()");

        String domain = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(domain);

        log.info("localRedirect() returns : " + domain);
        return redirectView;
    }

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request) {
        log.info("login(request=" + request + ")");

        String referrer = request.getHeader("referer");

        if (referrer != null) {
            log.info("referrer is not null");
            log.info("referrer= " + referrer);
            if(!referrer.contains("/login") && !referrer.contains("/client/resetPassword")) {
                request.getSession().setAttribute("url_prior_login", referrer);
            }
            log.info("end of if");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(authentication);
        if (authentication instanceof AnonymousAuthenticationToken || authentication.getAuthorities().contains(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"))) {
            log.info("login() returns : login");
            return "login";
        } else {
            log.info("client already logged in: login() returns index");
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/register")
    public ModelAndView register() {
        log.info("register()");

        ModelAndView model = new ModelAndView("register");
        model.addObject("client", new ClientJSON());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.info("register returns : register");
            return model;
        } else {
            log.info("client already registered: register() returns index");
            return new ModelAndView("redirect:/index");
        }
    }

    @RequestMapping(value = "/forgotPassword")
    public String forgot() {
        log.info("forgot()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.info("forgot() returns : forgotPass");
            return "forgotPass";
        } else {
            log.info("client already logged in: forgot() returns index");
            return "redirect:/index";
        }
    }

    @RequestMapping(value = "/search")
    public String search() {
        log.info("search()");
        log.info("search() returns : search");
        return "search";
    }

    @RequestMapping(value = "/403")
    public String error403() {
        log.info("error403()");

        log.info("error403() returns : 403");
        return "403";
    }

    @RequestMapping(value = "/404")
    public String error404() {
        log.info("error404()");

        log.info("error404() returns : 404");
        return "404";
    }

    @RequestMapping(value = "/500")
    public String error500() {
        log.info("error500()");

        log.info("error500() returns : 500");
        return "500";
    }

    @RequestMapping(value = "/best", method = RequestMethod.GET)
    public String best() {
        log.info("best()");
        log.info("best() returns : best");
        return "best";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile() {
        log.info("profile()");
        log.info("profile() returns : profile");
        return "profile";
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public String about() {
        log.info("about()");
        log.info("about() returns : about");
        return "about";
    }

    @RequestMapping(value = "/registrationConfirm/{token}", method = RequestMethod.GET)
    public String registrationConfirm(@PathVariable("token") String token) {
        log.info("registrationConfirm(token=" + token + ")");

        List<VerificationTokenDb> tokenDbs = tokenService.getVerificationToken(token);

        for (VerificationTokenDb verificationTokenDb : tokenDbs) {
            if (verificationTokenDb == null) {
                log.error("Error : There is no such token");

                throw new IllegalArgumentException("There is no such token");
            }

            ClientDb clientDb = verificationTokenDb.getClientByIdClient();
            Calendar cal = Calendar.getInstance();
            if ((verificationTokenDb.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
                log.error("Error : Verification token has expired");

                throw new IllegalArgumentException("Verification token has expired");
            }

            if (clientDb.getEnabled() == 0) {
                log.info("User is unblocked now");

                clientDb.setEnabled(1);
                clientService.saveOrUpdate(clientDb);
                tokenService.removeVerificationToken(verificationTokenDb);
                return "redirect:/login?confirmed=true";
            }

        }

        return "redirect:/index";
    }


}

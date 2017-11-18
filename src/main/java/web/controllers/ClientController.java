package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import web.dao.ClientDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.services.ClientService;

import java.util.Map;

@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired(required = true)
    private ClientService clientService;

    private static final Logger log = LogManager.getLogger(ClientController.class);

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addClient(@ModelAttribute("client") ClientJSON clientJSON) throws ParsingJsonToDaoException {
        log.info("addClient(clientJSON=" + clientJSON + ")");

        clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));

        log.info("addClient() returns : OK");
        return "OK";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteClient(@PathVariable("id") String id) {
        log.info("deleteClient(id=" + id + ")");

        clientService.delete(id);

        log.info("deleteClient() returns : OK");
        return "OK";
    }

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.POST)
    public ClientJSON getInfoAboutCurrentUser() {
        log.info("getInfoAboutCurrentUser()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        ClientJSON clientJSON = new ClientJSON();

        clientJSON.setEmail(clientDb.getEmail());
        clientJSON.setFirstName(clientDb.getFirstName());
        clientJSON.setLastName(clientDb.getLastName());
        clientJSON.setLogin(clientDb.getLogin());
        clientJSON.setPhoneNumber(clientDb.getPhoneNumber());

        log.info("getInfoAboutCurrentUser() returns : clientJSON=" + clientJSON);
        return clientJSON;
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(){
        log.info("profile()");
        log.info("profile() returns : profile");
        return "profile";
    }

    @RequestMapping(value = "/info" , method =  RequestMethod.GET)
    public ClientDb getInfo(){
        log.info("getInfo()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        return clientDb;

    }

}

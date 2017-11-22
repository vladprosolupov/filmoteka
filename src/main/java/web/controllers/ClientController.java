package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.ClientDb;
import web.events.OnRegistrationCompleteEvent;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.services.ClientService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired(required = true)
    private ClientService clientService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private static final Logger log = LogManager.getLogger(ClientController.class);

    @RequestMapping(value = "/loginCheck/{login}", method = RequestMethod.GET)
    public @ResponseBody
    boolean loginCheck(@PathVariable("login") String login) {
        log.info("loginCheck(login=" + login + ")");

        boolean result = clientService.loginCheck(login);

        log.info("loginCheck() returns: " + result);
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addClient(@ModelAttribute("client") @Valid ClientJSON clientJSON, BindingResult bindingResult, HttpServletRequest request) throws ParsingJsonToDaoException {
        log.info("addClient(clientJSON=" + clientJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("Customer does not pass validation");

            return "Error";
        }

        ClientDb clientDb = clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));
        if (clientDb == null) {
            log.error("Customer does not registered");

            return "Error";
        }

        String applicationURL = request.getContextPath();
        log.info("appURL - " + applicationURL);
        log.info("locale - " + request.getLocale());

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(applicationURL, clientDb, request.getLocale()));

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

    @RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
    public @ResponseBody
    ClientJSON getInfoAboutCurrentUser() {
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
}

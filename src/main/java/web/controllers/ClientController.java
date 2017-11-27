package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.dao.ClientDb;
import web.dao.PasswordResetTokenDb;
import web.events.OnRegistrationCompleteEvent;
import web.exceptions.NoSuchClientException;
import web.exceptions.ParsingJsonToDaoException;
import web.model.ClientJSON;
import web.model.ClientLoginJSON;
import web.model.ClientPasswordJSON;
import web.services.ClientService;
import web.services.PasswordGenerator;
import web.services.PasswordResetTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;


@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired(required = true)
    private ClientService clientService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService;

    @Qualifier(value = "messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

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

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody
    String editClient(@RequestBody @Valid ClientJSON clientJSON) throws ParsingJsonToDaoException {
        log.info("editClient(clientJSON=" + clientJSON + ")");

        if (clientJSON == null) {
            log.error("clientJSON is null");

            throw new IllegalArgumentException("ClientJSON should not be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());
        clientJSON.setId(clientDb.getId());
        clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));

        log.info("editClient() returns : OK");
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

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    public @ResponseBody
    String clientForgotPassword(@RequestBody String clientEmail) throws NoSuchClientException {
        log.info("clientForgotPassword(clientEmail=" + clientEmail + ")");

        if (clientEmail == null || clientEmail.isEmpty()) {
            log.error("clientEmail is null or empty");

            throw new IllegalArgumentException("ClientEmail should not be null or empty");
        }

        ClientDb clientDb = clientService.getClientByEmail(clientEmail);

        if (clientDb == null) {
            log.error("There is no such client with given email");

            throw new NoSuchClientException("There is no such client with given email");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetTokenDb passwordResetTokenDb = new PasswordResetTokenDb();
        passwordResetTokenDb.setClientByIdClient(clientDb);
        passwordResetTokenDb.setToken(token);

        passwordResetTokenService.savePasswordResetToken(passwordResetTokenDb);

        sendResetPasswordEmail(clientEmail, token, clientDb.getFirstName(), clientDb.getLastName());

        return "OK";

    }

    @RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("token") String token) {
        log.info("resetPassword(token=" + token + ")");

        PasswordResetTokenDb passwordResetTokenDb = passwordResetTokenService.getPasswordResetToken(token);

        if (passwordResetTokenDb == null) {
            log.error("No such token");

            throw new IllegalArgumentException("There is no such token");
        }

        Calendar cal = Calendar.getInstance();
        if ((passwordResetTokenDb.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            log.error("Error : Verification token has expired");

            throw new IllegalArgumentException("Verification token has expired");
        }

        ClientDb clientDb = passwordResetTokenDb.getClientByIdClient();
        Authentication auth = new UsernamePasswordAuthenticationToken(
                clientDb, null, Arrays.asList(
                new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        return "updatePass";
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    public String savePassword(@RequestBody @Valid ClientPasswordJSON clientPassword) {
        log.info("savePassword(clientPassword=" + clientPassword + ")");

        String name = SecurityContextHolder.getContext().getAuthentication().getName();

//        ClientDb clientDb = clientService.getClientByLogin(name);
//        clientDb.setPassword(PasswordGenerator.hashPassword(clientPassword.getPassword()));
//        clientService.saveOrUpdate(clientDb);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (GrantedAuthority g : authorities) {
            log.info("Authorities - " + g.getAuthority());
//            authorities.remove(g);
        }
//        for (GrantedAuthority g : authorities) {
//            log.info("Authorities  KEKEKEKEKE- " + g.getAuthority());
//        }
        Authentication auth = new AnonymousAuthenticationToken("ANONYMOUS_USER", null,
                Arrays.asList(
                        new SimpleGrantedAuthority("ANONYMOUS_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);


        log.info("succ. changed user password");
        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public List<ClientJSON> getAllClients() {
        log.info("getAllClients()");

        List<ClientDb> allClients = clientService.getAll();
        List<ClientJSON> allClientsJSON = new ArrayList<>();

        for (ClientDb clientDb : allClients) {
            log.info("for loop");

            ClientJSON clientJSON = new ClientJSON();

            clientJSON.setFirstName(clientDb.getFirstName());
            clientJSON.setLastName(clientDb.getLastName());
            clientJSON.setPhoneNumber(clientDb.getPhoneNumber());
            clientJSON.setLogin(clientDb.getEmail());
            clientJSON.setEmail(clientDb.getEmail());

            allClientsJSON.add(clientJSON);
        }

        log.info("getAllClients() returns : allClientsJSON.size()=" + allClientsJSON.size());
        return allClientsJSON;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/blockClient", method = RequestMethod.POST)
    public @ResponseBody
    String blockClient(@RequestBody @Valid ClientLoginJSON clientLogin) {
        log.info("blockClient(clientLogin=" + clientLogin + ")");

        if (clientLogin == null) {
            log.error("clientLogin is null");

            throw new IllegalArgumentException("ClientLoginJSON should not be empty");
        }

        clientService.blockClient(clientLogin.getLogin());

        return "OK";
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "/unblockClient", method = RequestMethod.POST)
    public @ResponseBody
    String unblockClient(@RequestBody @Valid ClientLoginJSON clientLogin) {
        log.info("unblockClient(clientLogin=" + clientLogin + ")");

        if (clientLogin == null) {
            log.error("clientLogin is null");

            throw new IllegalArgumentException("ClientLoginJSON should not be empty");
        }

        clientService.unblockClient(clientLogin.getLogin());

        return "OK";
    }

    // Simple methods.

    /**
     * This method sends email to the user.
     *
     * @param clientEmail
     * @param token
     * @param firstName
     * @param lastName
     */
    private void sendResetPasswordEmail(String clientEmail, String token, String firstName, String lastName) {
        log.info("sendResetPasswordEmail(clientEmail=" + clientEmail + ", token=" + token + ", firstName="
                + firstName + ", lastName=" + lastName + ")");

        try {
            String greet = messages.getMessage("message.greet", null, Locale.US);
            String head = messages.getMessage("message.resetPasswordHead", null, Locale.US);
            String foot = messages.getMessage("message.regFoot", null, Locale.US);
            String link = messages.getMessage("message.link", null, Locale.US);

            String confirmationUrl = "/client/resetPassword/" + token;

            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(clientEmail);
            email.setSubject("Password Reset");
            email.setText(greet + " " + firstName + " " + lastName + head + link + confirmationUrl + foot);
            mailSender.send(email);
        } catch (Exception e) {
            log.error("EXCEPTION - " + e);
        }

        log.info("mail was sent to user");
    }
}

package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
import web.model.ClientPassword;
import web.services.ClientService;
import web.services.PasswordGenerator;
import web.services.PasswordResetTokenService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;


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

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody String editClient(@RequestBody @Valid ClientJSON clientJSON) throws ParsingJsonToDaoException {
        log.info("editClient(clientJSON=" + clientJSON + ")");

        if(clientJSON == null) {
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
    public void clientForgotPassword(@RequestBody String clientEmail) throws NoSuchClientException {
        log.info("clientForgotPassword(clientEmail=" + clientEmail + ")");

        if(clientEmail == null || clientEmail.isEmpty()) {
            log.error("clientEmail is null or empty");

            throw new IllegalArgumentException("ClientEmail should not be null or empty");
        }

        ClientDb clientDb = clientService.getClientByEmail(clientEmail);

        if(clientDb == null) {
            log.error("There is no such client with given email");

            throw new NoSuchClientException("There is no such client with given email");
        }

        String token = UUID.randomUUID().toString();

        PasswordResetTokenDb passwordResetTokenDb = new PasswordResetTokenDb();
        passwordResetTokenDb.setClientByIdClient(clientDb);
        passwordResetTokenDb.setToken(token);

        passwordResetTokenService.savePasswordResetToken(passwordResetTokenDb);

        sendResetPasswordEmail(clientEmail, token, clientDb.getFirstName(), clientDb.getLastName());

    }

    @RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("token") String token) {
        log.info("resetPassword(token=" + token + ")");

        PasswordResetTokenDb passwordResetTokenDb = passwordResetTokenService.getPasswordResetToken(token);

        if(passwordResetTokenDb == null) {
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

        return "resetPassword";
    }

    @RequestMapping(value = "/savePassword", method = RequestMethod.POST)
    public String savePassword(@RequestBody @Valid ClientPassword clientPassword) {
        log.info("savePassword(clientPassword=" + clientPassword + ")");

        String name = SecurityContextHolder.getContext()
                .getAuthentication().getName();

        ClientDb clientDb = clientService.getClientByLogin(name);
        clientDb.setPassword(PasswordGenerator.hashPassword(clientPassword.getPassword()));
        clientService.saveOrUpdate(clientDb);

        log.info("succ. changed user password");
        return "/";
    }

    // Simple methods.

    /**
     * This method sends email to the user.
     * @param clientEmail
     * @param token
     * @param firstName
     * @param lastName
     */
    private void sendResetPasswordEmail(String clientEmail, String token, String firstName, String lastName) {
        log.info("sendResetPasswordEmail(clientEmail=" + clientEmail +", token=" + token + ", firstName="
                + firstName + ", lastName=" + lastName + ")");

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

        log.info("mail was sent to user");
    }
}

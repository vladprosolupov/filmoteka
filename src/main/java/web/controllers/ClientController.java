package web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import web.dao.ClientDb;
import web.dao.PasswordResetTokenDb;
import web.events.OnRegistrationCompleteEvent;
import web.exceptions.NoSuchClientException;
import web.exceptions.ParsingJsonToDaoException;
import web.exceptions.ValidationError;
import web.model.ClientJSON;
import web.model.ClientLoginJSON;
import web.model.ClientPasswordJSON;
import web.model.FilmJSONIndex;
import web.services.ClientService;
import web.services.PasswordGenerator;
import web.services.PasswordResetTokenService;
import web.tasks.EditClientTask;
import web.tasks.RegisterUserTask;
import web.tasks.SendForgotPasswordEmailTask;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Controller
@RequestMapping(value = "/client")
public class ClientController {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Qualifier(value = "messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;
//    @Autowired(required = true)
//    private ClientService clientService;
//
//    @Autowired
//    private PasswordResetTokenService passwordResetTokenService;
//
//    @Autowired
//    private RegisterUserTask registerUserTask;
//
//    @Autowired
//    private EditClientTask editClientTask;
//
//    @Autowired
//    private SendForgotPasswordEmailTask sendForgotPasswordEmailTask;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private static final Logger log = LogManager.getLogger(ClientController.class);

    @RequestMapping(value = "/loginCheck/{login}", method = RequestMethod.GET)
    public @ResponseBody
    boolean loginCheck(@PathVariable("login") String login) {
        log.info("loginCheck(login=" + login + ")");

        ClientService clientService = new ClientService(sessionFactory);
        boolean result = clientService.loginCheck(login);

        log.info("loginCheck() returns: " + result);
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody
    String addClient(@ModelAttribute("client") @Valid ClientJSON clientJSON, BindingResult bindingResult, HttpServletRequest request) throws ParsingJsonToDaoException, ValidationError {
        log.info("addClient(clientJSON=" + clientJSON + ")");

        if (bindingResult.hasErrors()) {
            log.error("Customer does not pass validation");

            throw new ValidationError("Validation is incorrect");
        }

        ClientService clientService = new ClientService(sessionFactory);
        ClientDb clientDb = clientService.saveOrUpdate(clientService.convertToClientDb(clientJSON));
        if (clientDb == null) {
            log.error("Customer does not registered");

            return "Error";
        }

        String applicationURL = request.getContextPath();

        RegisterUserTask registerUserTask = new RegisterUserTask();
        registerUserTask.setApplicationURL(applicationURL);
        registerUserTask.setClientDb(clientDb);
        registerUserTask.setEventPublisher(eventPublisher);
        executorService.execute(registerUserTask);

        log.info("addClient() returns : OK");
        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public @ResponseBody
    String editClient(@RequestBody @Valid ClientJSON clientJSON, BindingResult bindingResult) throws ParsingJsonToDaoException, ValidationError {
        log.info("editClient(clientJSON=" + clientJSON + ")");

        if (clientJSON == null) {
            log.error("clientJSON is null");

            throw new IllegalArgumentException("ClientJSON should not be null");
        }

        if (bindingResult.hasErrors()) {
            log.error("Validation error");

            throw new ValidationError("Validation is incorrect");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientService clientService = new ClientService(sessionFactory);

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());
        clientJSON.setId(clientDb.getId());

        EditClientTask editClientTask = new EditClientTask(sessionFactory);
        editClientTask.setClientJSON(clientJSON);
        executorService.execute(editClientTask);

        log.info("editClient() returns : OK");
        return "OK";
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public @ResponseBody
    String deleteClient(@PathVariable("id") String id) {
        log.info("deleteClient(id=" + id + ")");


        ClientService clientService = new ClientService(sessionFactory);
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

        ClientService clientService = new ClientService(sessionFactory);

        ClientDb clientDb = clientService.getClientByLogin(authentication.getName());

        ClientJSON clientJSON = new ClientJSON();

        clientJSON.setId(clientDb.getId());
        clientJSON.setEmail(clientDb.getEmail());
        clientJSON.setFirstName(clientDb.getFirstName());
        clientJSON.setLastName(clientDb.getLastName());
        clientJSON.setLogin(clientDb.getLogin());
        clientJSON.setPhoneNumber(clientDb.getPhoneNumber());
        clientJSON.setAvatar(clientDb.getAvatarByAvatar().getPath());

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

        ClientService clientService = new ClientService(sessionFactory);
        ClientDb clientDb = clientService.getClientByEmail(clientEmail);

        if (clientDb == null) {
            log.error("There is no such client with given email");

            throw new NoSuchClientException("There is no such client with given email");

        }

        String token = UUID.randomUUID().toString();

        PasswordResetTokenDb passwordResetTokenDb = new PasswordResetTokenDb();
        passwordResetTokenDb.setClientByIdClient(clientDb);
        passwordResetTokenDb.setToken(token);

        PasswordResetTokenService passwordResetTokenService = new PasswordResetTokenService(sessionFactory);
        passwordResetTokenService.savePasswordResetToken(passwordResetTokenDb);

        SendForgotPasswordEmailTask sendForgotPasswordEmailTask = new SendForgotPasswordEmailTask();
        sendForgotPasswordEmailTask.setClientEmail(clientEmail);
        sendForgotPasswordEmailTask.setToken(token);
        sendForgotPasswordEmailTask.setClientFirstName(clientDb.getFirstName());
        sendForgotPasswordEmailTask.setClientLastName(clientDb.getLastName());
        sendForgotPasswordEmailTask.setMailSender(mailSender);
        sendForgotPasswordEmailTask.setMessages(messages);

        executorService.execute(sendForgotPasswordEmailTask);

        return "OK";

    }

    @RequestMapping(value = "/resetPassword/{token}", method = RequestMethod.GET)
    public String resetPassword(@PathVariable("token") String token) {
        log.info("resetPassword(token=" + token + ")");

        PasswordResetTokenService passwordResetTokenService = new PasswordResetTokenService(sessionFactory);
        PasswordResetTokenDb passwordResetTokenDb = passwordResetTokenService.getPasswordResetToken(token);

        if (passwordResetTokenDb == null) {
            log.error("No such token");

            log.info("no such token found: resetPassword() returns index");
            return "redirect:/index?token=false";
        }

        Calendar cal = Calendar.getInstance();
        if ((passwordResetTokenDb.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            log.error("Error : Verification token has expired");

            throw new IllegalArgumentException("Verification token has expired");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE"))) {
            log.info("resetPassword() Authority CHANGE_PASSWORD_PRIVILEGE is already set");
            log.info("resetPassword() returns : updatePass");
            return "updatePass";
        } else {
            ClientDb clientDb = passwordResetTokenDb.getClientByIdClient();
            log.info("resetPassword() sets Authority to CHANGE_PASSWORD_PRIVILEGE");
            Authentication auth = new UsernamePasswordAuthenticationToken(
                    clientDb.getLogin(), null, Arrays.asList(
                    new SimpleGrantedAuthority("CHANGE_PASSWORD_PRIVILEGE")));
            SecurityContextHolder.getContext().setAuthentication(auth);
            log.info("resetPassword() returns : updatePass");
            return "updatePass";
        }

    }

    @PreAuthorize("hasAuthority('CHANGE_PASSWORD_PRIVILEGE')")
    @RequestMapping(value = "/savePassword/{token}", method = RequestMethod.POST)
    public @ResponseBody
    String savePassword(@RequestBody @Valid ClientPasswordJSON clientPassword, @PathVariable("token") String token) {
        log.info("savePassword(clientPassword=" + clientPassword + ", token=" + token + ")");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        ClientService clientService = new ClientService(sessionFactory);

        ClientDb clientDb = clientService.getClientByLogin(name);
        clientDb.setPassword(PasswordGenerator.hashPassword(clientPassword.getPassword()));
        clientService.saveOrUpdate(clientDb);

        Authentication auth = new AnonymousAuthenticationToken("ANONYMOUS_USER", clientDb.getLogin(), Arrays.asList(new SimpleGrantedAuthority("ANONYMOUS_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);

        PasswordResetTokenService passwordResetTokenService = new PasswordResetTokenService(sessionFactory);

        PasswordResetTokenDb passwordResetTokenDb = passwordResetTokenService.getPasswordResetToken(token);
        passwordResetTokenService.removePasswordResetToken(passwordResetTokenDb);

        log.info("succ. changed user password");

        return "OK";
    }

    @PreAuthorize("hasAnyAuthority('admin','user')")
    @RequestMapping(value = "/filmsForSuggestion/{page}", method = RequestMethod.GET)
    public @ResponseBody
    List<FilmJSONIndex> getSuggestionsForPage(@PathVariable("page") String page) {
        log.info("getSuggestionsForPage(page= " + page + ")");
        int pageNum = Integer.parseInt(page);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }


        ClientService clientService = new ClientService(sessionFactory);
        List<FilmJSONIndex> list = clientService.getFilmsForSuggestion(pageNum, clientService.getClientIdByLogin(authentication.getName()));

        log.info("getSuggestionsForPage() returns : list.size() = " + list.size());
        return list;
    }

    @PreAuthorize("hasAnyAuthority('admin', 'user')")
    @RequestMapping(value = "/numberOfSuggested", method = RequestMethod.GET)
    public @ResponseBody
    long getNumberOfSuggested() {
        log.info("getNumberOfSuggested()");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ((authentication instanceof AnonymousAuthenticationToken)) {
            log.error("If statement, user is not logged in, throwing exception");

            throw new IllegalArgumentException("User is not logged in");
        }

        ClientService clientService = new ClientService(sessionFactory);
        long result = clientService.getNumberOfSuggested(clientService.getClientIdByLogin(authentication.getName()));

        log.info("getNumberOfSuggested() returns : result = " + result);
        return result;
    }

    @PreAuthorize("hasAuthority('admin')")
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public @ResponseBody
    List<ClientJSON> getAllClients() {
        log.info("getAllClients()");

        //List<ClientDb> allClients = clientService.getAll();
        ClientService clientService = new ClientService(sessionFactory);
        List<ClientJSON> allClientsJSON = clientService.getAllForAdmin();

        /*for (ClientDb clientDb : allClients) {
            log.info("for loop");

            ClientJSON clientJSON = new ClientJSON();

            clientJSON.setFirstName(clientDb.getFirstName());
            clientJSON.setLastName(clientDb.getLastName());
            clientJSON.setPhoneNumber(clientDb.getPhoneNumber());
            clientJSON.setLogin(clientDb.getEmail());
            clientJSON.setEmail(clientDb.getEmail());

            allClientsJSON.add(clientJSON);
        }*/

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

        ClientService clientService = new ClientService(sessionFactory);
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

        ClientService clientService = new ClientService(sessionFactory);
        clientService.unblockClient(clientLogin.getLogin());

        return "OK";
    }

}

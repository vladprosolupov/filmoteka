package web.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.dao.VerificationTokenDb;
import web.events.OnRegistrationCompleteEvent;
import web.services.VerificationTokenService;

import java.util.Locale;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    @Qualifier(value = "messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private VerificationTokenService tokenService;

    private static final Logger log = LogManager.getLogger(RegistrationListener.class);

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        log.info("onApplicationEvent(onRegistrationCompleteEvent=" + onRegistrationCompleteEvent + ")");

        this.confirmRegistration(onRegistrationCompleteEvent);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        log.info("confirmRegistration(event=" + event + ")");

        ClientDb client = event.getClientDb();
        String token = UUID.randomUUID().toString();

        VerificationTokenDb tokenDb = new VerificationTokenDb();
        tokenDb.setToken(token);
        tokenDb.setClientByIdClient(client);

        tokenService.saveToken(tokenDb);

        String recipientAddress = client.getEmail();
        log.info("get customer email : " + recipientAddress);
        String subject = "Registration Confirmation";
        log.info("created subject : " + subject);
        String confirmationUrl
                = event.getApplicationUrl() + "/registrationConfirm/" + token;
        log.info("created confirmationUrl : " + confirmationUrl);

        try {
            String message = messages.getMessage("message.regSucc", null, Locale.US);
            log.info("created message : " + message);

            SimpleMailMessage email = new SimpleMailMessage();
            log.info("created email : ");
            email.setTo(recipientAddress);
            log.info("set recipient address : " + email.getTo()[0]);
            email.setSubject(subject);
            log.info("set subject : " + email.getSubject());
            email.setText(message + "/nLink for confirmation your account: " + "http://localhost:8080" + confirmationUrl);
            log.info("set text : " + email.getText());
            mailSender.send(email);
            log.info("sent email");
        } catch (Exception e) {
            log.error(e);
        }

        log.info("mail sent to the user");
    }
}

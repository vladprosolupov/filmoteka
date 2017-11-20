package web.listeners;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import web.dao.ClientDb;
import web.dao.VerificationTokenDb;
import web.events.OnRegistrationCompleteEvent;
import web.services.VerificationTokenService;

import java.util.UUID;

public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

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
        String subject = "Registration Confirmation";
        String confirmationUrl
                = event.getApplicationUrl() + "/registrationConfirm.html?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale());

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText(message + " rn" + "http://localhost:8080" + confirmationUrl);
        mailSender.send(email);

    }
}

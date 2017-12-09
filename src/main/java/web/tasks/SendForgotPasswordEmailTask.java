package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("SendForgotPasswordEmailTask")
public class SendForgotPasswordEmailTask implements Runnable {

    @Qualifier(value = "messageSource")
    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    private String clientEmail;
    private String token;
    private String clientFirstName;
    private String clientLastName;

    private static final Logger log = LogManager.getLogger(SendForgotPasswordEmailTask.class);

    @Override
    public void run() {
        log.info("run()");

        sendResetPasswordEmail(clientEmail, token, clientFirstName, clientLastName);

        log.info("succ. sent email");
    }

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

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getClientFirstName() {
        return clientFirstName;
    }

    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }

    public String getClientLastName() {
        return clientLastName;
    }

    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
}

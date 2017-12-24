package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.events.OnRegistrationCompleteEvent;
import web.services.ClientService;

import java.util.Locale;

@Component("RegisterUserTask")
public class RegisterUserTask implements Runnable {

    private ApplicationEventPublisher eventPublisher;
    private String applicationURL;
    private ClientDb clientDb;

    private static final Logger log = LogManager.getLogger(RegisterUserTask.class);

    @Override
    public void run() {
        log.info("run(); applicationURL=" + applicationURL + ", clientDb=" + clientDb);

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(applicationURL, clientDb));

        log.info("email sended");
    }

    public String getApplicationURL() {
        return applicationURL;
    }

    public void setApplicationURL(String applicationURL) {
        this.applicationURL = applicationURL;
    }

    public ClientDb getClientDb() {
        return clientDb;
    }

    public void setClientDb(ClientDb clientDb) {
        this.clientDb = clientDb;
    }

    public ApplicationEventPublisher getEventPublisher() {
        return eventPublisher;
    }

    public void setEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}

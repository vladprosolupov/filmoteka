package web.events;

import org.springframework.context.ApplicationEvent;
import web.dao.ClientDb;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String applicationUrl;
    private ClientDb clientDb;

    public OnRegistrationCompleteEvent(String applicationUrl, ClientDb clientDb) {
        super(clientDb);

        this.applicationUrl = applicationUrl;
        this.clientDb = clientDb;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public ClientDb getClientDb() {
        return clientDb;
    }

    public void setClientDb(ClientDb clientDb) {
        this.clientDb = clientDb;
    }
}

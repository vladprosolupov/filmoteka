package web.events;

import org.springframework.context.ApplicationEvent;
import web.dao.ClientDb;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private String applicationUrl;
    private Locale locale;
    private ClientDb clientDb;

    public OnRegistrationCompleteEvent(String applicationUrl, ClientDb clientDb, Locale locale) {
        super(clientDb);

        this.applicationUrl = applicationUrl;
        this.locale = locale;
        this.clientDb = clientDb;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public ClientDb getClientDb() {
        return clientDb;
    }

    public void setClientDb(ClientDb clientDb) {
        this.clientDb = clientDb;
    }
}

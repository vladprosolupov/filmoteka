package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.services.ClientDataService;

@Component("RemoveFromClientDataTask")
public class RemoveFromClientDataTask implements Runnable {

    private SessionFactory sessionFactory;

    private String filmId;
    private String clientId;

    private static final Logger log = LogManager.getLogger(RemoveFromClientDataTask.class);

    public RemoveFromClientDataTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run()");

        ClientDataService clientDataService = new ClientDataService(sessionFactory);
        clientDataService.removeFilm(filmId, clientId);

        log.info("succ. removed removed film from user data");
    }

    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

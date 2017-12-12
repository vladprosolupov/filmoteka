package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.services.ClientDataService;

@Component("RemoveFromClientDataTask")
public class RemoveFromClientDataTask implements Runnable {

    @Autowired
    private ClientDataService clientDataService;

    private String filmId;
    private String clientId;

    private static final Logger log = LogManager.getLogger(RemoveFromClientDataTask.class);

    @Override
    public void run() {
        log.info("run()");

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

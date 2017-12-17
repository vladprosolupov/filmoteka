package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.FilmLikesJSON;
import web.services.FilmLikesService;

@Component("RemoveClientLikeTask")
public class RemoveClientLikeTask implements Runnable {

    private SessionFactory sessionFactory;

    private int clientId;
    private FilmLikesJSON filmLikesJSON;

    private static final Logger log = LogManager.getLogger(RemoveClientLikeTask.class);

    public RemoveClientLikeTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run(); clientId=" + clientId + ", filmLikeJSON=" + filmLikesJSON);

        FilmLikesService likesService = new FilmLikesService(sessionFactory);

        likesService.deleteLike(Integer.toString(filmLikesJSON.getFilmId()), Integer.toString(clientId));

        log.info("succ. removed like");
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public FilmLikesJSON getFilmLikesJSON() {
        return filmLikesJSON;
    }

    public void setFilmLikesJSON(FilmLikesJSON filmLikesJSON) {
        this.filmLikesJSON = filmLikesJSON;
    }
}

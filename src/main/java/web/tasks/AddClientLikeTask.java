package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.FilmLikesJSON;
import web.services.FilmLikesService;

@Component("AddClientLikeTask")
public class AddClientLikeTask implements Runnable {

    @Autowired
    private FilmLikesService likesService;

    private ClientDb clientDb;
    private FilmLikesJSON filmLikesJSON;

    private static final Logger log = LogManager.getLogger(AddClientLikeTask.class);

    @Override
    public void run() {
        log.info("run(); clientDb=" + clientDb + ", filmLikeJSON=" + filmLikesJSON);

        likesService.addLike(likesService.convertToFilmLikeDbFromFilmLike(likesService.convertToFilmLikeFromFilmLikesJSON(filmLikesJSON, clientDb)));

        log.info("succ added like");
    }

    public ClientDb getClientDb() {
        return clientDb;
    }

    public void setClientDb(ClientDb clientDb) {
        this.clientDb = clientDb;
    }

    public FilmLikesJSON getFilmLikesJSON() {
        return filmLikesJSON;
    }

    public void setFilmLikesJSON(FilmLikesJSON filmLikesJSON) {
        this.filmLikesJSON = filmLikesJSON;
    }
}

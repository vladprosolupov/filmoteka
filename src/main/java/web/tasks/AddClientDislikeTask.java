package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.FilmLikesJSON;
import web.services.FilmDislikesService;

@Component("AddClientDislikeTask")
public class AddClientDislikeTask implements Runnable {

    @Autowired
    private FilmDislikesService dislikesService;

    private ClientDb clientDb;
    private FilmLikesJSON filmLikesJSON;

    private static final Logger log = LogManager.getLogger(AddClientDislikeTask.class);

    @Override
    public void run() {
        log.info("run()");

        dislikesService.addDislike(dislikesService.convertToFilmLikeDbFromFilmLike(dislikesService.convertToFilmDislikeFromFilmLikesJSON(filmLikesJSON, clientDb)));

        log.info("succ. added dislike");
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

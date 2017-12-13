package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.model.FilmLikesJSON;
import web.services.FilmDislikesService;

@Component("RemoveClientDislikeTask")
public class RemoveClientDislikeTask implements Runnable {

    @Autowired
    private FilmDislikesService dislikesService;

    private int clientId;
    private FilmLikesJSON filmLikesJSON;

    private static final Logger log = LogManager.getLogger(RemoveClientDislikeTask.class);

    @Override
    public void run() {
        log.info("run(); clientId=" + clientId + ", filmLikeJSON=" + filmLikesJSON);

        dislikesService.deleteDislike(Integer.toString(filmLikesJSON.getFilmId()), Integer.toString(clientId));

        log.info("succ. deleted dislike");
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

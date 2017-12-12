package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.FilmLikesJSON;
import web.services.FilmDislikesService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("AddClientDislikeTask")
public class AddClientDislikeTask implements Runnable {

    @Autowired
    private FilmDislikesService dislikesService;

    @Autowired
    private RemoveFromClientDataTask removeFromClientDataTask;

    private ClientDb clientDb;
    private FilmLikesJSON filmLikesJSON;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private static final Logger log = LogManager.getLogger(AddClientDislikeTask.class);

    @Override
    public void run() {
        log.info("run(); clinetDb=" + clientDb + ", filmLikeJSON=" + filmLikesJSON);

        dislikesService.addDislike(dislikesService.convertToFilmLikeDbFromFilmLike(dislikesService.convertToFilmDislikeFromFilmLikesJSON(filmLikesJSON, clientDb)));

        removeFromClientDataTask.setClientId(Integer.toString(clientDb.getId()));
        removeFromClientDataTask.setFilmId(Integer.toString(filmLikesJSON.getFilmId()));
        executorService.submit(removeFromClientDataTask);

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

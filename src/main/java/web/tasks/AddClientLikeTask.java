package web.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web.dao.ClientDb;
import web.model.FilmLikesJSON;
import web.services.AiService;
import web.services.FilmLikesService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AddClientLikeTask implements Runnable {

    private SessionFactory sessionFactory;

    private ExecutorService executorService = Executors.newCachedThreadPool();

    private ClientDb clientDb;
    private FilmLikesJSON filmLikesJSON;

    private static final Logger log = LogManager.getLogger(AddClientLikeTask.class);

    public AddClientLikeTask(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void run() {
        log.info("run(); clientDb=" + clientDb + ", filmLikeJSON=" + filmLikesJSON);

        AiTask aiTask = new AiTask(sessionFactory);
        aiTask.setCurrentClient(clientDb);
        executorService.execute(aiTask);

        FilmLikesService likesService = new FilmLikesService(sessionFactory);

        likesService.addLike(
                likesService.convertToFilmLikeDbFromFilmLike(
                        likesService.convertToFilmLikeFromFilmLikesJSON(filmLikesJSON, clientDb)));


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

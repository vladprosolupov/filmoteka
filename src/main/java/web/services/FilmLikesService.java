package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDb;
import web.dao.FilmDb;
import web.dao.FilmLikeDb;
import web.embeddable.FilmLike;
import web.model.FilmLikesJSON;

import java.util.List;

@Service("FilmLikesService")
@Transactional
public class FilmLikesService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    private static final Logger log = LogManager.getLogger(FilmLikesService.class);

    public void addLike(FilmLikeDb filmLikeDb) throws HibernateException {
        log.info("addLike(filmLikeDb=" + filmLikeDb + ")");

        if(filmLikeDb == null) {
            log.error("filmLikeDb is null");

            throw new IllegalArgumentException("FilmLikeDb is null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(filmLikeDb);
        session.getTransaction().commit();;
        session.close();

        log.info("succ. saved like for film");
    }

    public void deleteLike(String filmId, String clientId) throws HibernateException, IndexOutOfBoundsException {
        log.info("deleteLike(filmId=" + filmId + ", clientId=" + clientId + ")");

        if(filmId == null || filmId.isEmpty() || filmId.equals("undefined")) {
            log.error("filmId is null or empty or undefined");

            throw new IllegalArgumentException("filmId is null or empty or undefined");
        }

        if(clientId == null || clientId.isEmpty() || clientId.equals("undefined")) {
            log.error("clientId is null or empty or undefined");

            throw new IllegalArgumentException("clientId is null or empty or undefined");
        }


        Session session = sessionFactory.openSession();
        session.beginTransaction();

        FilmLikeDb filmLikeDb = (FilmLikeDb) session.createQuery("from FilmLikeDb fl where fl.filmLike.clientByIdClient=" + clientId + " and fl.filmLike.filmByIdFilm=" + filmId).list().get(0);

        if(filmLikeDb == null) {
            log.error("This client hasn't liked this film");

            session.getTransaction().commit();
            session.close();

            throw new IllegalArgumentException("This client hasn't liked this film");
        }

        session.delete(filmLikeDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted like for film");
    }

    public int getLikesForFilm(String id) {
        log.info("getLikesForFilm(id=" + id + ")");

        if(id == null || id.equals("undefined")) {
            log.error("id is null or undefined");

            throw new IllegalArgumentException("Id is null or undefined");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("count(fl.filmLike.filmByIdFilm) from FilmLikeDb fl where fl.filmLike.filmByIdFilm=" + id).list();
        session.getTransaction().commit();
        session.close();

        return list.size();
    }

    public FilmLike convertToFilmLikeFromFilmLikesJSON(FilmLikesJSON filmLikesJSON, ClientDb clientDb) {
        log.info("convertToFilmLikeFromFilmLikesJSON(filmLikesJSON=" + filmLikesJSON + ")");

        FilmDb filmDb = filmService.getFilmWithId(Integer.toString(filmLikesJSON.getFilmId()));

        if(filmDb == null) {
            log.error("There is no such film");

            throw new IllegalArgumentException("There is no such film");
        }


        FilmLike filmLike = new FilmLike();
        filmLike.setFilmByIdFilm(filmDb);
        filmLike.setClientByIdClient(clientDb);

        log.info("convertToFilmLikeFromFilmLikesJSON() returns : filmLike=" + filmLike);
        return filmLike;
    }

    public FilmLikeDb convertToFilmLikeDbFromFilmLike(FilmLike filmLike) {
        log.info("convertToFilmLikeDbFromFilmLike(filmLike=" + filmLike + ")");

        if(filmLike == null) {
            log.error("filmLike is null");

            throw new IllegalArgumentException("FilmLike should not be null");
        }

        FilmLikeDb filmLikeDb = new FilmLikeDb();
        filmLikeDb.setFilmLike(filmLike);

        log.info("convertToFilmLikeDbFromFilmLike() returns : filmLikeDb=" + filmLikeDb);
        return filmLikeDb;
    }
}

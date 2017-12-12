package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ClientDb;
import web.dao.FilmDb;
import web.dao.FilmDislikeDb;
import web.embeddable.FilmDislike;
import web.model.FilmLikesJSON;

import java.util.List;

@Service("FilmDislikesService")
@Transactional
public class FilmDislikesService {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    @Autowired
    private FilmLikesService filmLikesService;

    private static final Logger log = LogManager.getLogger(FilmDislikesService.class);

    public void addDislike(FilmDislikeDb filmDislikeDb) {
        log.info("addDislike(filmDislikeDb=" + filmDislikeDb + ")");

        if(filmDislikeDb == null) {
            log.error("filmDislikeDb is null");

            throw new IllegalArgumentException("FilmDislikeDb is null");
        }

        if(filmLikesService.checkLikeFilmByUserId(Integer.toString(filmDislikeDb.getFilmDislike().getFilmByIdFilm().getId()),
                filmDislikeDb.getFilmDislike().getClientByIdClient().getId()) == false) {
            log.info("There was like");

            filmLikesService.deleteLike(Integer.toString(filmDislikeDb.getFilmDislike().getFilmByIdFilm().getId()),
                    Integer.toString(filmDislikeDb.getFilmDislike().getClientByIdClient().getId()));

            log.info("succ. removed like");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(filmDislikeDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved like for film");
    }

    public void deleteDislike(String filmId, String clientId) throws HibernateException, IndexOutOfBoundsException {
        log.info("deleteDislike(filmId=" + filmId + ", clientId=" + clientId + ")");

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

        session.createQuery("delete from FilmDislikeDb fl " +
                "where fl.filmDislike.clientByIdClient=? and fl.filmDislike.filmByIdFilm=?")
                .setParameter(0, clientId).setParameter(1, filmId).executeUpdate();

        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted like for film");
    }

    public long getDislikesForFilm(String id) {
        log.info("getDislikesForFilm(id=" + id + ")");

        if(id == null || id.equals("undefined")) {
            log.error("id is null or undefined");

            throw new IllegalArgumentException("Id is null or undefined");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long)session.createQuery("select count(fd.filmDislike.clientByIdClient) from FilmDislikeDb fd where fd.filmDislike.filmByIdFilm.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();
        log.info("getDislikesForFilm() returns : result=" + result);
        return result;
    }

    public FilmDislike convertToFilmDislikeFromFilmLikesJSON(FilmLikesJSON filmLikesJSON, ClientDb clientDb) {
        log.info("convertToFilmDislikeFromFilmLikesJSON(filmLikesJSON=" + filmLikesJSON + ")");

        FilmDb filmDb = filmService.getFilmWithId(Integer.toString(filmLikesJSON.getFilmId()));

        if(filmDb == null) {
            log.error("There is no such film");

            throw new IllegalArgumentException("There is no such film");
        }


        FilmDislike filmDislike = new FilmDislike();
        filmDislike.setClientByIdClient(clientDb);
        filmDislike.setFilmByIdFilm(filmDb);

        log.info("convertToFilmDislikeFromFilmLikesJSON() returns : filmDislike=" + filmDislike);
        return filmDislike;
    }

    public FilmDislikeDb convertToFilmLikeDbFromFilmLike(FilmDislike filmDislike) {
        log.info("convertToFilmLikeDbFromFilmLike(filmDislike=" + filmDislike + ")");

        if(filmDislike == null) {
            log.error("filmDislike is null");

            throw new IllegalArgumentException("filmDislike should not be null");
        }

        FilmDislikeDb filmDislikeDb = new FilmDislikeDb();
        filmDislikeDb.setFilmDislike(filmDislike);

        log.info("convertToFilmLikeDbFromFilmLike() returns : filmDislikeDb=" + filmDislikeDb);
        return filmDislikeDb;
    }

    public boolean checkDislikeFilm(String id, ClientDb clientDb) {
        log.info("checkDislikeFilm(idFilm=" + id + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("select fd.id from FilmDislikeDb fd where fd.id.clientByIdClient.id = " + clientDb.getId() + " and fd.id.filmByIdFilm.id = " + id).list();
        session.getTransaction().commit();
        session.close();

        boolean result = !list.isEmpty();
        log.info("checkDislikeFilm() returns :" + result);

        return result;
    }


    public boolean checkDislikeFilmWithClientId(String id, int clientId) {
        log.info("checkDislikeFilmWithClientId(idFilm=" + id + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("select fd.id from FilmDislikeDb fd where fd.id.clientByIdClient.id = " + clientId + " and fd.id.filmByIdFilm.id = " + id).list();
        session.getTransaction().commit();
        session.close();

        boolean result = !list.isEmpty();
        log.info("checkDislikeFilmWithClientId() returns :" + result);

        return result;
    }
}

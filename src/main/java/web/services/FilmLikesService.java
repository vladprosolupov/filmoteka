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
import web.model.FilmJSONIndex;
import web.model.FilmLikesJSON;

import java.util.List;
import static java.lang.Math.toIntExact;

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

    public long getLikesForFilm(String id) {
        log.info("getLikesForFilm(id=" + id + ")");

        if(id == null || id.equals("undefined")) {
            log.error("id is null or undefined");

            throw new IllegalArgumentException("Id is null or undefined");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long)session.createQuery("select count(fl.filmLike.filmByIdFilm.id) from FilmLikeDb fl where fl.filmLike.filmByIdFilm.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();
        log.info("getLikesForFilm() returns : result=" + result);
        return result;
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

    public List<FilmJSONIndex> getLikedFilmsByUser(ClientDb clientDb, String p) throws HibernateException {
        log.info("getLikedFilmsByUser(clientDb=" + clientDb + ", p=" + p + ")");

        int page = Integer.parseInt(p);
        int limit = 10;
        int start = (page - 1) * limit;

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<FilmJSONIndex> list =
                session.createQuery("select fl.filmLike.filmByIdFilm.title, fl.filmLike.filmByIdFilm.releaseDate, " +
                        "fl.filmLike.filmByIdFilm.cover, fl.filmLike.filmByIdFilm.id, fl.filmLike.filmByIdFilm.rating " +
                        "from FilmLikeDb fl where fl.filmLike.clientByIdClient=" + clientDb.getId())
                        .setFirstResult(start).setMaxResults(limit).list();
        session.getTransaction().commit();
        session.close();

        log.info("getLikedFilmsByUser() returns : list.size()=" + list.size());
        return list;
    }

    public List<FilmJSONIndex> getLikedFilmsByUserId(int clientId, String p) throws HibernateException {
        log.info("getLikedFilmsByUserId(clientId=" + clientId + ", p=" + p + ")");

        int page = Integer.parseInt(p);
        int limit = 6;
        int start = (page - 1) * limit;

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<FilmJSONIndex> list =
                session.createQuery("select fl.filmLike.filmByIdFilm.title, fl.filmLike.filmByIdFilm.releaseDate, " +
                        "fl.filmLike.filmByIdFilm.cover, fl.filmLike.filmByIdFilm.id, fl.filmLike.filmByIdFilm.rating " +
                        "from FilmLikeDb fl where fl.filmLike.clientByIdClient=" + clientId)
                        .setFirstResult(start).setMaxResults(limit).list();
        session.getTransaction().commit();
        session.close();

        log.info("getLikedFilmsByUserId() returns : list.size()=" + list.size());
        return list;
    }

    public boolean checkLikeFilm(String id, ClientDb clientDb) {
        log.info("checkLikeFilm(idFilm=" + id + ")");


        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("select fl.id from FilmLikeDb fl where fl.filmLike.clientByIdClient.id = " + clientDb.getId() + " and fl.filmLike.filmByIdFilm.id = " + id).list();
        session.getTransaction().commit();
        session.close();

        boolean result = !list.isEmpty();

        log.info("checkLikeFilm() returns :" + result);
        return result;
    }

    public boolean checkLikeFilmByUserId(String id, int clientId) {
        log.info("checkLikeFilmByUserId(idFilm=" + id + ")");


        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List list = session.createQuery("select fl.id from FilmLikeDb fl where fl.filmLike.clientByIdClient.id = " + clientId + " and fl.filmLike.filmByIdFilm.id = " + id).list();
        session.getTransaction().commit();
        session.close();

        boolean result = !list.isEmpty();

        log.info("checkLikeFilmByUserId() returns :" + result);
        return result;
    }

    public long getNumbersOfLikeByUser(ClientDb clientDb) {
        log.info("getNumbersOfLikeByUser(clientDb=" + clientDb + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(fl.filmLike.filmByIdFilm.id) from FilmLikeDb fl where fl.filmLike.clientByIdClient.id=" + clientDb.getId()).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumbersOfLikeByUser() returns : result=" + result);
        return result;
    }

    public long getNumbersOfLikeByUserId(int clientId) {
        log.info("getNumbersOfLikeByUserId(clientDb=" + clientId + ")");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        long result = (long) session.createQuery("select count(fl.filmLike.filmByIdFilm.id) from FilmLikeDb fl where fl.filmLike.clientByIdClient.id=" + clientId).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getNumbersOfLikeByUserId() returns : result=" + result);
        return result;
    }

}

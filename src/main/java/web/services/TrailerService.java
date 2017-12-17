package web.services;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TrailerDb;
import web.exceptions.ParsingJsonToDaoException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("TrailerService")
@Transactional
public class TrailerService {

//    @Autowired
    private SessionFactory sessionFactory;

    public TrailerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    //    @Autowired
//    private FilmService filmService;

    private static final Logger log = LogManager.getLogger(TrailerService.class);

    public TrailerDb getTrailerWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getTrailerWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        TrailerDb trailerDb =
                (TrailerDb) session.createQuery("from TrailerDb t where t.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getTrailerWithId() returns : trailerDb=" + trailerDb);
        return trailerDb;
    }

    public void saveOrUpdate(TrailerDb trailerDb) throws HibernateException {
        log.info("saveOrUpdate(trailerDb=" + trailerDb + ")");

        if (trailerDb == null) {
            log.error("Error : trailerDb is null");

            throw new IllegalArgumentException("TrailerDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(trailerDb);
        session.getTransaction().commit();
        session.close();

        log.info("succ. saved or updated trailer");
    }

    public void delete(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from TrailerDb t where t.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted trailer");
    }

    public List<TrailerDb> getAll() throws HibernateException {
        log.info("getAll()");

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<TrailerDb> result = session.createQuery("from TrailerDb ").list();
        session.getTransaction().commit();
        session.close();

        log.info("getAll() returns : result.size()=" + result.size());
        return result;
    }

    public Set<TrailerDb> createTrailerDbSet(List<String> trailers) throws ParsingJsonToDaoException {
        log.info("createTrailerDbSet(trailers=" + trailers + ")");

        if (trailers == null) {
            log.error("Error : trailers is null");

            throw new IllegalArgumentException("Trailers should not be null");
        }

        Set<TrailerDb> trailerDbSet = new HashSet<>();
        for (String s : trailers) {
            log.info("for loop");

            TrailerDb trailerDb = new TrailerDb();
            trailerDb.setLink(s);
            trailerDbSet.add(trailerDb);
        }

        log.info("createTrailerDbSet() returns : trailerDbSet.size()=" + trailerDbSet.size());
        return trailerDbSet;
    }

    public void checkForTrailers(int idFilm, Set<TrailerDb> trailerDbSet) throws HibernateException {
        log.info("checkForTrailers(idFilm=" + idFilm + ", trailerDbSet=" + trailerDbSet + ")");

        if (idFilm < 0) {
            log.error("Error : idFilm is incorrect");

            throw new IllegalArgumentException("IdFilm should not be null");
        }
        if (trailerDbSet == null) {
            log.error("Error : trailerDbSet is null");

            throw new IllegalArgumentException("TrailerDbSer should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from TrailerDb t where t.filmByIdFilm=" + idFilm).executeUpdate();
        FilmService filmService = new FilmService(sessionFactory);

        for (TrailerDb trailerDb : trailerDbSet) {
            log.info("for loop");
            trailerDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(trailerDb);
        }
        session.getTransaction().commit();
        session.close();

        log.info("checked");
    }
}

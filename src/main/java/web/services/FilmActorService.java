package web.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.ActorDb;
import web.dao.FilmActorDb;
import web.exceptions.ParsingJsonToDaoException;
import web.model.FilmActorJSON;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("FilmActorService")
@Transactional
public class FilmActorService {

    private SessionFactory sessionFactory;

    private static final Logger log = LogManager.getLogger(FilmActorService.class);

    public FilmActorService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FilmActorDb getFilmActorWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getFilmActorWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        FilmActorDb filmActorDb =
                (FilmActorDb) session.createQuery("from FilmActorDb fa where fa.id=" + id).list().get(0);
        session.getTransaction().commit();
        session.close();

        log.info("getFilmActorWithId() returns : filActorDb=" + filmActorDb);
        return filmActorDb;
    }

    public int saveOrUpdate(FilmActorDb filmActorDb) throws HibernateException {
        log.info("saveOrUpdate(filmActorDb=" + filmActorDb + ")");

        if (filmActorDb == null) {
            log.error("Error : filmActorDb is null");

            throw new IllegalArgumentException("FilmActorDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.saveOrUpdate(filmActorDb);
        session.getTransaction().commit();
        session.close();

        log.info("saveOrUpdate() returns : filmActorDb.getId()=" + filmActorDb.getId());
        return filmActorDb.getId();
    }

    public void deleteFilmActor(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from FilmActorDb fa where fa.id=" + id).executeUpdate();
        session.getTransaction().commit();
        session.close();

        log.info("succ. deleted film actor");
    }

    public Set<FilmActorDb> createSetOfFilmActor(Map<String, Integer> actors) throws ParsingJsonToDaoException {
        log.info("createSetOfFilmActor(actors=" + actors + ")");

        if (actors == null) {
            log.error("Error : actors is null");

            throw new IllegalArgumentException("Actors should not be null");
        }

        ActorService actorService = new ActorService(sessionFactory);

        Set<FilmActorDb> actorDbSet = new HashSet<>();
        for (Map.Entry<String, Integer> m : actors.entrySet()) {
            log.info("for loop");

            FilmActorDb filmActorDb = new FilmActorDb();
            filmActorDb.setRole(m.getKey());
            filmActorDb.setActorByIdActor(actorService.getActorWithId(m.getValue()));
            actorDbSet.add(filmActorDb);
        }

        log.info("createSetOfFilmActor() returns : actorDbSet.size()=" + actorDbSet.size());
        return actorDbSet;
    }

    public void checkForFilmActors(int idFilm, Set<FilmActorDb> filmActorDbSet) throws HibernateException {
        log.info("checkForFilmActors(idFilm=" + idFilm + ", filmActorDbSet=" + filmActorDbSet + ")");

        if (idFilm < 0) {
            log.error("Error : idFilm is incorrect");

            throw new IllegalArgumentException("IdFilm should not be null");
        }
        if (filmActorDbSet == null) {
            log.error("Error : filmActorDbSet is null");

            throw new IllegalArgumentException("FilmActorDbSet should not be null");
        }

        FilmService filmService = new FilmService(sessionFactory);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createQuery("delete from FilmActorDb f where f.filmByIdFilm=" + idFilm).executeUpdate();
        session.getTransaction().commit();
        session.close();

        for (FilmActorDb filmActorDb : filmActorDbSet) {
            log.info("for loop");

            filmActorDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));

            Session session1 = sessionFactory.openSession();
            session1.beginTransaction();
            session1.saveOrUpdate(filmActorDb);
            session1.getTransaction().commit();
            session1.close();
        }


        log.info("checked");
    }
}

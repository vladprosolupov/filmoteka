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

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    @Autowired
    private ActorService actorService;

    @Autowired
    private FilmService filmService;

    private static final Logger log = LogManager.getLogger(FilmActorService.class);

    public FilmActorDb getFilmActorWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        log.info("getFilmActorWithId(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.openSession();
        FilmActorDb filmActorDb =
                (FilmActorDb) session.createQuery("from FilmActorDb fa where fa.id=" + id).list().get(0);
        session.close();

        log.info("getFilmActorWithId() returns : filActorDb=" + filmActorDb);
        return filmActorDb;
    }

    public int saveFilmActor(FilmActorDb filmActorDb) throws HibernateException {
        log.info("saveFilmActor(filmActorDb=" + filmActorDb + ")");

        if (filmActorDb == null) {
            log.error("Error : filmActorDb is null");

            throw new IllegalArgumentException("FilmActorDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.save(filmActorDb);
        session.close();

        log.info("saveFilmActor() returns : filmActorDb.getId()=" + filmActorDb.getId());
        return filmActorDb.getId();
    }

    public int updateFilmActor(FilmActorDb filmActorDb) throws HibernateException {
        log.info("updateFilmActor(filmActorDb=" + filmActorDb + ")");

        if (filmActorDb == null) {
            log.error("Error : filmActorDb is null");

            throw new IllegalArgumentException("FilmActorDb should not be null");
        }

        Session session = sessionFactory.openSession();
        session.update(filmActorDb);
        session.close();

        log.info("updateFilmActor() returns : filmActorDb.getId=" + filmActorDb.getId());
        return filmActorDb.getId();
    }

    public void deleteFilmActor(String id) throws HibernateException {
        log.info("delete(id=" + id + ")");

        if (id == null || id.isEmpty()) {
            log.error("Error : id is incorrect");

            throw new IllegalArgumentException("Id should not be null or empty");
        }

        Session session = sessionFactory.openSession();
        session.createQuery("delete from FilmActorDb fa where fa.id=" + id).executeUpdate();
        session.close();

        log.info("succ. deleted film actor");
    }

    public Set<FilmActorDb> createSetOfFilmActor(Map<String, Integer> actors) throws ParsingJsonToDaoException {
        log.info("createSetOfFilmActor(actors=" + actors + ")");

        if (actors == null) {
            log.error("Error : actors is null");

            throw new IllegalArgumentException("Actors should not be null");
        }

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

        Session session = sessionFactory.openSession();
        session.createQuery("delete from FilmActorDb f where f.filmByIdFilm=" + idFilm).executeUpdate();
        for (FilmActorDb filmActorDb : filmActorDbSet) {
            log.info("for loop");

            filmActorDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(filmActorDb);
        }
        session.close();

        log.info("checked");
    }
}

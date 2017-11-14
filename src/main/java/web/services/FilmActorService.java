package web.services;

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

    public FilmActorDb getFilmActorWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        FilmActorDb filmActorDb =
                (FilmActorDb) session.createQuery("from FilmActorDb fa where fa.id=" + id).list().get(0);
        return filmActorDb;
    }

    public int saveFilmActor(FilmActorDb filmActorDb) throws HibernateException {
        if (filmActorDb == null) {
            throw new IllegalArgumentException("FilmActorDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.save(filmActorDb);
        return filmActorDb.getId();
    }

    public int updateFilmActor(FilmActorDb filmActorDb) throws HibernateException {
        if (filmActorDb == null) {
            throw new IllegalArgumentException("FilmActorDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.update(filmActorDb);
        return filmActorDb.getId();
    }

    public void deleteFilmActor(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from FilmActorDb fa where fa.id=" + id).executeUpdate();
    }

    public Set<FilmActorDb> createSetOfFilmActor(Map<String, Integer> actors) throws ParsingJsonToDaoException {
        if (actors == null) {
            throw new IllegalArgumentException("Actors should not be null");
        }
        Set<FilmActorDb> actorDbSet = new HashSet<>();
        for (Map.Entry<String, Integer> m : actors.entrySet()) {
            FilmActorDb filmActorDb = new FilmActorDb();
            filmActorDb.setRole(m.getKey());
            filmActorDb.setActorByIdActor(actorService.getActorWithId(m.getValue()));
            actorDbSet.add(filmActorDb);
        }
        return actorDbSet;
    }

    public void checkForFilmActors(int idFilm, Set<FilmActorDb> filmActorDbSet) throws HibernateException {
        if (idFilm < 0) {
            throw new IllegalArgumentException("IdFilm should not be null");
        }
        if (filmActorDbSet == null) {
            throw new IllegalArgumentException("FilmActorDbSet should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from FilmActorDb f where f.filmByIdFilm=" + idFilm).executeUpdate();
        for (FilmActorDb filmActorDb : filmActorDbSet) {
            filmActorDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(filmActorDb);
        }
    }
}

package web.services;


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

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private FilmService filmService;

    public TrailerDb getTrailerWithId(String id) throws HibernateException, IndexOutOfBoundsException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        TrailerDb trailerDb =
                (TrailerDb) session.createQuery("from TrailerDb t where t.id=" + id).list().get(0);
        return trailerDb;
    }

    public void saveOrUpdate(TrailerDb trailerDb) throws HibernateException {
        if (trailerDb == null) {
            throw new IllegalArgumentException("TrailerDb should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(trailerDb);
    }

    public void delete(String id) throws HibernateException {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Id should not be null or empty");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TrailerDb t where t.id=" + id).executeUpdate();
    }

    public List<TrailerDb> getAll() throws HibernateException {
        Session session = sessionFactory.getCurrentSession();
        List<TrailerDb> result = session.createQuery("from TrailerDb ").list();
        return result;
    }

    public Set<TrailerDb> createTrailerDbSet(List<String> trailers) throws ParsingJsonToDaoException {
        if (trailers == null) {
            throw new IllegalArgumentException("Trailers should not be null");
        }
        Set<TrailerDb> trailerDbSet = new HashSet<>();
        for (String s : trailers) {
            TrailerDb trailerDb = new TrailerDb();
            trailerDb.setLink(s);
            trailerDbSet.add(trailerDb);
        }
        return trailerDbSet;
    }

    public void checkForTrailers(int idFilm, Set<TrailerDb> trailerDbSet) throws HibernateException {
        if (idFilm < 0) {
            throw new IllegalArgumentException("IdFilm should not be null");
        }
        if (trailerDbSet == null) {
            throw new IllegalArgumentException("TrailerDbSer should not be null");
        }
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TrailerDb t where t.filmByIdFilm=" + idFilm).executeUpdate();
        for (TrailerDb trailerDb : trailerDbSet) {
            trailerDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(trailerDb);
        }
    }
}

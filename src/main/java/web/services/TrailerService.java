package web.services;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.TrailerDb;

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

    public TrailerDb getTrailerWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        TrailerDb trailerDb =
                (TrailerDb) session.createQuery("from TrailerDb t where t.id=" + id).list().get(0);
        return trailerDb;
    }

    public void saveOrUpdate(TrailerDb trailerDb){
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(trailerDb);
    }

    public void delete(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TrailerDb t where t.id=" + id).executeUpdate();
    }

    public List<TrailerDb> getAll(){
        Session session = sessionFactory.getCurrentSession();
        List<TrailerDb> result = session.createQuery("from TrailerDb ").list();
        return result;
    }

    public Set<TrailerDb> createTrailerDbSet(List<String> trailers){
        Set<TrailerDb> trailerDbSet = new HashSet<>();
        for (String s : trailers) {
            TrailerDb trailerDb = new TrailerDb();
            trailerDb.setLink(s);
            trailerDbSet.add(trailerDb);
        }
        return trailerDbSet;
    }

    public void checkForTrailers(int idFilm, Set<TrailerDb> trailerDbSet){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from TrailerDb t where t.filmByIdFilm=" + idFilm).executeUpdate();
        for (TrailerDb trailerDb : trailerDbSet){
            trailerDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(idFilm)));
            session.saveOrUpdate(trailerDb);
        }
    }
}

package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.FilmActorDb;

import java.io.Serializable;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Service("FilmActorService")
@Transactional
public class FilmActorService {

    @Autowired(required = true)
    private SessionFactory sessionFactory;

    public FilmActorDb getFilmActorWithId(String idActor, String role, int idFilm){
        Session session = sessionFactory.getCurrentSession();
        FilmActorDb filmActorDb =
                (FilmActorDb) session.createQuery("from FilmActorDb fa where fa.actorByIdActor=" + idActor
                        + " and fa.role=" + role
                        + " and fa.filmByIdFilm=" + idFilm).list().get(0);
        return filmActorDb;
    }

    public int saveFilmActor(FilmActorDb filmActorDb){
        Session session = sessionFactory.getCurrentSession();
        session.save(filmActorDb);
        return filmActorDb.getId();
    }
}

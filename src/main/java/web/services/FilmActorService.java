package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import web.dao.FilmActorDb;

/**
 * Created by Rostyk on 16.06.2017.
 */
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
}

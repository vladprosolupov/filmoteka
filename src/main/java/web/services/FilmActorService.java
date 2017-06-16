package web.services;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.FilmActorDb;
import web.model.FilmActorJSON;


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

    public FilmActorDb getFilmActorWithId(String id){
        Session session = sessionFactory.getCurrentSession();
        FilmActorDb filmActorDb =
                (FilmActorDb) session.createQuery("from FilmActorDb fa where fa.id=" + id).list().get(0);
        return filmActorDb;
    }

    public int saveFilmActor(FilmActorDb filmActorDb){
        Session session = sessionFactory.getCurrentSession();
        session.save(filmActorDb);
        return filmActorDb.getId();
    }

    public int updateFilmActor(FilmActorDb filmActorDb){
        Session session = sessionFactory.getCurrentSession();
        session.update(filmActorDb);
        return filmActorDb.getId();
    }

    public void deleteFilmActor(String id){
        Session session = sessionFactory.getCurrentSession();
        session.createQuery("delete from FilmActorDb fa where fa.id=" + id).executeUpdate();
    }

    public FilmActorDb convert(FilmActorJSON filmActorJSON){
        FilmActorDb filmActorDb = new FilmActorDb();
        filmActorDb.setActorByIdActor(actorService.getActorWithId(filmActorJSON.getIdActor()));
        filmActorDb.setFilmByIdFilm(filmService.getFilmWithId(Integer.toString(filmActorJSON.getIdFilm())));
        return filmActorDb;
    }
}

package web.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import web.dao.FilmActorDb;
import web.services.ActorService;
import web.services.FilmService;

/**
 * Created by Rostyk on 16.06.2017.
 */
@Repository
public class FilmActorJSON {

    private int id;

    private int idFilm;

    private int idActor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

}

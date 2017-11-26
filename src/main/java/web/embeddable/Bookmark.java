package web.embeddable;

import web.dao.ClientDb;
import web.dao.FilmDb;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class Bookmark implements Serializable{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false)
    private FilmDb filmByIdFilm;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
    private ClientDb clientByIdClient;


    public FilmDb getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmDb filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }


    public ClientDb getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientDb clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @Override
    public String toString() {
        return "Bookmark{" +
                "filmByIdFilm=" + filmByIdFilm +
                ", clientByIdClient=" + clientByIdClient +
                '}';
    }
}

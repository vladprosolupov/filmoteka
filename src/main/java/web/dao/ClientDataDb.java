package web.dao;

import javax.persistence.*;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Client_data", schema = "dbo", catalog = "filmotekaDb")
public class ClientDataDb {
    private int id;
    private ClientDb clientByIdClient;
    private FilmDb filmByIdFilm;
    private int aiPoints;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDataDb that = (ClientDataDb) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
    public ClientDb getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientDb clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false)
    public FilmDb getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmDb filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }

    @Basic
    @Column(name = "ai_points", nullable = false)
    public int getAiPoints() {
        return aiPoints;
    }

    public void setAiPoints(int aiPoints) {
        this.aiPoints = aiPoints;
    }

    @Override
    public String toString() {
        return "ClientDataDb{" +
                "id=" + id +
                ", clientByIdClient=" + clientByIdClient +
                ", filmByIdFilm=" + filmByIdFilm +
                ", aiPoints=" + aiPoints +
                '}';
    }
}

package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Film_Actor", schema = "dbo", catalog = "inzS13009")
public class FilmActorDb {
    private int id;
    private String role;
    @JsonIgnore
    private FilmDb filmByIdFilm;
    private ActorDb actorByIdActor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "role", nullable = true, length = -1)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmActorDb that = (FilmActorDb) o;

        if (id != that.id) return false;
        if (role != null ? !role.equals(that.role) : that.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false)
    public FilmDb getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmDb filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }

    @ManyToOne
    @JoinColumn(name = "id_actor", referencedColumnName = "id", nullable = false)
    public ActorDb getActorByIdActor() {
        return actorByIdActor;
    }

    public void setActorByIdActor(ActorDb actorByIdActor) {
        this.actorByIdActor = actorByIdActor;
    }

    @Override
    public String toString() {
        return "FilmActorDb{" +
                "id=" + id +
                ", role='" + role + '\'' +
                ", filmByIdFilm=" + filmByIdFilm +
                ", actorByIdActor=" + actorByIdActor +
                '}';
    }
}

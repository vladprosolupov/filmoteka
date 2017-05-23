package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Film_Actor", schema = "dbo", catalog = "filmotekaDb")
public class FilmActorEntity {
    private int id;
    private String role;
    private FilmEntity filmByIdFilm;
    private ActorEntity actorByIdActor;

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
    @Column(name = "role", nullable = true, length = 64)
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

        FilmActorEntity that = (FilmActorEntity) o;

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
    public FilmEntity getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmEntity filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }

    @ManyToOne
    @JoinColumn(name = "id_actor", referencedColumnName = "id", nullable = false)
    public ActorEntity getActorByIdActor() {
        return actorByIdActor;
    }

    public void setActorByIdActor(ActorEntity actorByIdActor) {
        this.actorByIdActor = actorByIdActor;
    }
}

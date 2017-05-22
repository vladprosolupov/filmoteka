package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Trailer", schema = "dbo", catalog = "filmotekaDb")
public class TrailerEntity {
    private int id;
    private String link;
    private FilmEntity filmByIdFilm;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "link", nullable = true, length = -1)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrailerEntity that = (TrailerEntity) o;

        if (id != that.id) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (link != null ? link.hashCode() : 0);
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
}

package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Film_studio", schema = "dbo", catalog = "filmotekaDb")
public class FilmStudioEntity {
    private int id;
    private FilmEntity filmByIdFilm;
    private StudioEntity studioByIdStudio;

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

        FilmStudioEntity that = (FilmStudioEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
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
    @JoinColumn(name = "id_studio", referencedColumnName = "id", nullable = false)
    public StudioEntity getStudioByIdStudio() {
        return studioByIdStudio;
    }

    public void setStudioByIdStudio(StudioEntity studioByIdStudio) {
        this.studioByIdStudio = studioByIdStudio;
    }
}

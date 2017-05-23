package com.group.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Studio", schema = "dbo", catalog = "filmotekaDb")
public class StudioEntity {
    private int id;
    private String studioName;
    private Collection<FilmStudioEntity> filmStudiosById;

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
    @Column(name = "studio_name", nullable = true, length = 64)
    public String getStudioName() {
        return studioName;
    }

    public void setStudioName(String studioName) {
        this.studioName = studioName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudioEntity that = (StudioEntity) o;

        if (id != that.id) return false;
        if (studioName != null ? !studioName.equals(that.studioName) : that.studioName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studioName != null ? studioName.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "studioByIdStudio")
    public Collection<FilmStudioEntity> getFilmStudiosById() {
        return filmStudiosById;
    }

    public void setFilmStudiosById(Collection<FilmStudioEntity> filmStudiosById) {
        this.filmStudiosById = filmStudiosById;
    }
}

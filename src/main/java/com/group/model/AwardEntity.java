package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Award", schema = "dbo", catalog = "filmotekaDb")
public class AwardEntity {
    private int id;
    private String awardName;
    private Integer awardYear;
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
    @Column(name = "award_name", nullable = true, length = 64)
    public String getAwardName() {
        return awardName;
    }

    public void setAwardName(String awardName) {
        this.awardName = awardName;
    }

    @Basic
    @Column(name = "award_year", nullable = true)
    public Integer getAwardYear() {
        return awardYear;
    }

    public void setAwardYear(Integer awardYear) {
        this.awardYear = awardYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AwardEntity that = (AwardEntity) o;

        if (id != that.id) return false;
        if (awardName != null ? !awardName.equals(that.awardName) : that.awardName != null) return false;
        if (awardYear != null ? !awardYear.equals(that.awardYear) : that.awardYear != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (awardName != null ? awardName.hashCode() : 0);
        result = 31 * result + (awardYear != null ? awardYear.hashCode() : 0);
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

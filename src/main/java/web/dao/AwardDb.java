package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Award", schema = "dbo", catalog = "filmotekaDb")
public class AwardDb {
    private int id;
    private String awardName;
    private Integer awardYear;
    @JsonIgnore
    private FilmDb filmByIdFilm;

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

        AwardDb awardDb = (AwardDb) o;

        if (id != awardDb.id) return false;
        if (awardName != null ? !awardName.equals(awardDb.awardName) : awardDb.awardName != null) return false;
        if (awardYear != null ? !awardYear.equals(awardDb.awardYear) : awardDb.awardYear != null) return false;

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
    public FilmDb getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmDb filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }
}

package dao;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Studio", schema = "dbo", catalog = "filmotekaDb")
public class StudioDb {
    private int id;
    private String studioName;
    private Set<FilmDb> filmStudios;

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

        StudioDb studioDb = (StudioDb) o;

        if (id != studioDb.id) return false;
        if (studioName != null ? !studioName.equals(studioDb.studioName) : studioDb.studioName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (studioName != null ? studioName.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "filmStudios")
    public Set<FilmDb> getFilmStudios() {
        return filmStudios;
    }

    public void setFilmStudios(Set<FilmDb> filmStudios) {
        this.filmStudios = filmStudios;
    }
}

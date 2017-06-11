package dao;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Category", schema = "dbo", catalog = "filmotekaDb")
public class CategoryDb {
    private int id;
    private String name;
    private Set<FilmDb> filmCategories;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 128)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CategoryDb that = (CategoryDb) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "filmCategories")
    public Set<FilmDb> getFilmCategories() {
        return filmCategories;
    }

    public void setFilmCategories(Set<FilmDb> filmCategories) {
        this.filmCategories = filmCategories;
    }
}

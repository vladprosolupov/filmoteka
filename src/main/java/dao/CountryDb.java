package dao;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Country", schema = "dbo", catalog = "filmotekaDb")
public class CountryDb {
    private int id;
    private String name;
    private Collection<ActorDb> actorsById;
    private Collection<CityDb> citiesById;
    private Collection<DirectorDb> directorsById;
    private Set<FilmDb> coutryFilms;
    private Set<FilmDb> countryFilms;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 32)
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

        CountryDb countryDb = (CountryDb) o;

        if (id != countryDb.id) return false;
        if (name != null ? !name.equals(countryDb.name) : countryDb.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<ActorDb> getActorsById() {
        return actorsById;
    }

    public void setActorsById(Collection<ActorDb> actorsById) {
        this.actorsById = actorsById;
    }

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<CityDb> getCitiesById() {
        return citiesById;
    }

    public void setCitiesById(Collection<CityDb> citiesById) {
        this.citiesById = citiesById;
    }

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<DirectorDb> getDirectorsById() {
        return directorsById;
    }

    public void setDirectorsById(Collection<DirectorDb> directorsById) {
        this.directorsById = directorsById;
    }

    @ManyToMany(mappedBy = "filmCountries")
    public Set<FilmDb> getCoutryFilms() {
        return coutryFilms;
    }

    public void setCoutryFilms(Set<FilmDb> coutryFilms) {
        this.coutryFilms = coutryFilms;
    }

    @ManyToMany(mappedBy = "filmCountries")
    public Set<FilmDb> getCountryFilms() {
        return countryFilms;
    }

    public void setCountryFilms(Set<FilmDb> countryFilms) {
        this.countryFilms = countryFilms;
    }
}

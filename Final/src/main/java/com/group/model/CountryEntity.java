package com.group.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Country", schema = "dbo", catalog = "filmotekaDb")
public class CountryEntity {
    private int id;
    private String name;
    private Collection<ActorEntity> actorsById;
    private Collection<CityEntity> citiesById;
    private Collection<DirectorEntity> directorsById;
    private Collection<FilmEntity> filmsById;

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

        CountryEntity that = (CountryEntity) o;

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

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<ActorEntity> getActorsById() {
        return actorsById;
    }

    public void setActorsById(Collection<ActorEntity> actorsById) {
        this.actorsById = actorsById;
    }

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<CityEntity> getCitiesById() {
        return citiesById;
    }

    public void setCitiesById(Collection<CityEntity> citiesById) {
        this.citiesById = citiesById;
    }

    @OneToMany(mappedBy = "countryByIdCountry")
    public Collection<DirectorEntity> getDirectorsById() {
        return directorsById;
    }

    public void setDirectorsById(Collection<DirectorEntity> directorsById) {
        this.directorsById = directorsById;
    }

    @OneToMany(mappedBy = "countryByCountryId")
    public Collection<FilmEntity> getFilmsById() {
        return filmsById;
    }

    public void setFilmsById(Collection<FilmEntity> filmsById) {
        this.filmsById = filmsById;
    }
}

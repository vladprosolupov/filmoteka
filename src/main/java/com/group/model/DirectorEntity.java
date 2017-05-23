package com.group.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Director", schema = "dbo", catalog = "filmotekaDb")
public class DirectorEntity {
    private int id;
    private String firstName;
    private String lastName;
    private CountryEntity countryByIdCountry;
    private Collection<FilmDirectorEntity> filmDirectorsById;

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
    @Column(name = "first_name", nullable = true, length = 64)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 64)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DirectorEntity that = (DirectorEntity) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_country", referencedColumnName = "id", nullable = false)
    public CountryEntity getCountryByIdCountry() {
        return countryByIdCountry;
    }

    public void setCountryByIdCountry(CountryEntity countryByIdCountry) {
        this.countryByIdCountry = countryByIdCountry;
    }

    @OneToMany(mappedBy = "directorByIdDirector")
    public Collection<FilmDirectorEntity> getFilmDirectorsById() {
        return filmDirectorsById;
    }

    public void setFilmDirectorsById(Collection<FilmDirectorEntity> filmDirectorsById) {
        this.filmDirectorsById = filmDirectorsById;
    }
}

package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Actor", schema = "dbo", catalog = "inzS13009")
public class ActorDb {
    private int id;
    private String firstName;
    private String lastName;
    //private Date birthdate;
    //private CountryDb countryByIdCountry;
    @JsonIgnore
    private Collection<FilmActorDb> filmActorsById;

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

//    @Basic
//    @Column(name = "birthdate", nullable = true)
//    public Date getBirthdate() {
//        return birthdate;
//    }
//
//    public void setBirthdate(Date birthdate) {
//        this.birthdate = birthdate;
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ActorDb actorDb = (ActorDb) o;

        if (id != actorDb.id) return false;
        if (firstName != null ? !firstName.equals(actorDb.firstName) : actorDb.firstName != null) return false;
        if (lastName != null ? !lastName.equals(actorDb.lastName) : actorDb.lastName != null) return false;
        //if (birthdate != null ? !birthdate.equals(actorDb.birthdate) : actorDb.birthdate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        //result = 31 * result + (birthdate != null ? birthdate.hashCode() : 0);
        return result;
    }

//    @ManyToOne
//    @JoinColumn(name = "id_country", referencedColumnName = "id", nullable = false)
//    public CountryDb getCountryByIdCountry() {
//        return countryByIdCountry;
//    }
//
//    public void setCountryByIdCountry(CountryDb countryByIdCountry) {
//        this.countryByIdCountry = countryByIdCountry;
//    }

    @OneToMany(mappedBy = "actorByIdActor")
    public Collection<FilmActorDb> getFilmActorsById() {
        return filmActorsById;
    }

    public void setFilmActorsById(Collection<FilmActorDb> filmActorsById) {
        this.filmActorsById = filmActorsById;
    }

    @Override
    public String toString() {
        return "ActorDb{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
               //", birthdate=" + birthdate +
               // ", countryByIdCountry=" + countryByIdCountry +
                '}';
    }
}

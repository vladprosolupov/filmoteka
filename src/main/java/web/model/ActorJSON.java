package web.model;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class ActorJSON {

    private int id;
    @NotNull
    @Size(min = 2, max = 64, message = "First name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "[a-zA-Z+]", message = "First name should only include letters")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 64, message = "Last name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "[a-zA-Z+]", message = "Last name should only include letters")
    private String lastName;
    @NotNull
    @Past
    private String birthdate;
    private String country;
    //private List<String> idFilmActor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

//    public List<String> getIdFilmActor() {
//        return idFilmActor;
//    }
//
//    public void setIdFilmActor(List<String> idFilmActor) {
//        this.idFilmActor = idFilmActor;
//    }


    @Override
    public String toString() {
        return "ActorJSON{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

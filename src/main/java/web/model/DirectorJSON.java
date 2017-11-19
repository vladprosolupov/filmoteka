package web.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by Rostyk on 17.06.2017.
 */
public class DirectorJSON {

    private int id;
    @NotNull
    @Size(min = 2, max = 64, message = "First name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First name should only include letters")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 64, message = "Last name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last name should only include letters")
    private String lastName;
    private String country;

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "DirectorJSON{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}

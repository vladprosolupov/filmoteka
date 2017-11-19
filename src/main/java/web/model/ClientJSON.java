package web.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import web.enums.ClientRole;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ClientJSON {

    //    private int id;
    @NotNull
    @Size(min = 2, max = 64, message = "First name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "[a-zA-Z+]", message = "First name should only include letters")
    private String firstName;
    @NotNull
    @Size(min = 2, max = 64, message = "Last name should at least have 2 symbols and it should be not bigger than 64")
    @Pattern(regexp = "[a-zA-Z+]", message = "Last name should only include letters")
    private String lastName;
    @Email
    @NotNull
    private String email;
    //    private int idAddress;
    @NotNull
    @Size(min = 2, max = 64)
    private String login;
    @NotNull
    @Size(min = 5, max = 60)
    private String password;
    //    private int enabled;
    @NotNull
    @Length(min = 7, max = 20)
    private String phoneNumber;
//    private ClientRole role;

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

//    public int getIdAddress() {
//        return idAddress;
//    }
//
//    public void setIdAddress(int idAddress) {
//        this.idAddress = idAddress;
//    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public int getEnabled() {
//        return enabled;
//    }
//
//    public void setEnabled(int enabled) {
//        this.enabled = enabled;
//    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

//    public ClientRole getRole() {
//        return role;
//    }
//
//    public void setRole(ClientRole role) {
//        this.role = role;
//    }


    @Override
    public String toString() {
        return "ClientJSON{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

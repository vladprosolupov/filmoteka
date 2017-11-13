package web.model;

import web.enums.ClientRole;

public class ClientJSON {

    //    private int id;
    private String firstName;
    private String lastName;
    private String email;
    //    private int idAddress;
    private String login;
    private String password;
    //    private int enabled;
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

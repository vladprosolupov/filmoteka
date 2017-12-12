package web.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Client", schema = "dbo", catalog = "filmotekaDb")
public class ClientDb {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore
    private Timestamp creationDate;
    private String login;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private int enabled;
    private String phoneNumber;
    @JsonIgnore
    private String role;
    //@JsonIgnore
    //private Collection<ClientDataDb> clientDataById;
    private AvatarDb avatarByAvatar;

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

    @Basic
    @Column(name = "email", nullable = true, length = 128)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "creation_date", nullable = true)
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "login", nullable = false, length = 64)
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Basic
    @Column(name = "password", nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "enabled", nullable = false, columnDefinition = "int default 1")
    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    @Basic
    @Column(name = "phone_number", nullable = true, length = 20)
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Basic
    @Column(name = "role", nullable = true, length = 10)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientDb clientDb = (ClientDb) o;

        if (id != clientDb.id) return false;
        if (enabled != clientDb.enabled) return false;
        if (firstName != null ? !firstName.equals(clientDb.firstName) : clientDb.firstName != null) return false;
        if (lastName != null ? !lastName.equals(clientDb.lastName) : clientDb.lastName != null) return false;
        if (email != null ? !email.equals(clientDb.email) : clientDb.email != null) return false;
        if (creationDate != null ? !creationDate.equals(clientDb.creationDate) : clientDb.creationDate != null)
            return false;
        if (login != null ? !login.equals(clientDb.login) : clientDb.login != null) return false;
        if (password != null ? !password.equals(clientDb.password) : clientDb.password != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(clientDb.phoneNumber) : clientDb.phoneNumber != null)
            return false;
        if (role != null ? !role.equals(clientDb.role) : clientDb.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + enabled;
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

//    @OneToMany(mappedBy = "clientByIdClient", fetch = FetchType.LAZY)
//    public Collection<ClientDataDb> getClientDataById() {
//        return clientDataById;
//    }
//
//    public void setClientDataById(Collection<ClientDataDb> clientDataById) {
//        this.clientDataById = clientDataById;
//    }

    @Override
    public String toString() {
        return "ClientDb{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", creationDate=" + creationDate +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", avatarByAvatar=" + avatarByAvatar +
                '}';
    }

    @ManyToOne
    @JoinColumn(name = "avatar", referencedColumnName = "id")
    public AvatarDb getAvatarByAvatar() {
        return avatarByAvatar;
    }

    public void setAvatarByAvatar(AvatarDb avatarByAvatar) {
        this.avatarByAvatar = avatarByAvatar;
    }
}

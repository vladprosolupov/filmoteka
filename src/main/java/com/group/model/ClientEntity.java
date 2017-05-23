package com.group.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Client", schema = "dbo", catalog = "filmotekaDb")
public class ClientEntity {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private Timestamp creationDate;
    private String login;
    private AddressEntity addressByIdAddress;
    private Collection<ClientDataEntity> clientDataById;
    private Collection<CommentRatingEntity> commentRatingsById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ClientEntity that = (ClientEntity) o;

        if (id != that.id) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;
        if (email != null ? !email.equals(that.email) : that.email != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        if (login != null ? !login.equals(that.login) : that.login != null) return false;

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
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_address", referencedColumnName = "id", nullable = false)
    public AddressEntity getAddressByIdAddress() {
        return addressByIdAddress;
    }

    public void setAddressByIdAddress(AddressEntity addressByIdAddress) {
        this.addressByIdAddress = addressByIdAddress;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<ClientDataEntity> getClientDataById() {
        return clientDataById;
    }

    public void setClientDataById(Collection<ClientDataEntity> clientDataById) {
        this.clientDataById = clientDataById;
    }

    @OneToMany(mappedBy = "clientByIdClient")
    public Collection<CommentRatingEntity> getCommentRatingsById() {
        return commentRatingsById;
    }

    public void setCommentRatingsById(Collection<CommentRatingEntity> commentRatingsById) {
        this.commentRatingsById = commentRatingsById;
    }
}

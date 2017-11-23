package web.dao;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "PasswordResetToken", schema = "dbo", catalog = "filmotekaDb")
public class PasswordResetTokenDb {
    private long id;
    private String token;
    private Timestamp expiryDate;
    private ClientDb clientByIdClient;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token", nullable = false, length = 36)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "expiryDate", nullable = false)
    public Timestamp getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Timestamp expiryDate) {
        this.expiryDate = expiryDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordResetTokenDb that = (PasswordResetTokenDb) o;

        if (id != that.id) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (expiryDate != null ? !expiryDate.equals(that.expiryDate) : that.expiryDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expiryDate != null ? expiryDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
    public ClientDb getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientDb clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }
}

package web.dao;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "VerificationToken", schema = "dbo", catalog = "filmotekaDb")
public class VerificationTokenDb {

    private long id;
    private String token;
    private Timestamp expirenceDate;
    private ClientDb clientByIdClient;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "expirence_date")
    public Timestamp getExpirenceDate() {
        return expirenceDate;
    }

    public void setExpirenceDate(Timestamp expirenceDate) {
        this.expirenceDate = expirenceDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VerificationTokenDb that = (VerificationTokenDb) o;

        if (id != that.id) return false;
        if (token != null ? !token.equals(that.token) : that.token != null) return false;
        if (expirenceDate != null ? !expirenceDate.equals(that.expirenceDate) : that.expirenceDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (token != null ? token.hashCode() : 0);
        result = 31 * result + (expirenceDate != null ? expirenceDate.hashCode() : 0);
        return result;
    }

    @OneToOne(targetEntity = ClientDb.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client", referencedColumnName = "id", nullable = false)
    public ClientDb getClientByIdClient() {
        return clientByIdClient;
    }

    public void setClientByIdClient(ClientDb clientByIdClient) {
        this.clientByIdClient = clientByIdClient;
    }
}

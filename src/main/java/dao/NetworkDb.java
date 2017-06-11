package dao;

import javax.persistence.*;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Network", schema = "dbo", catalog = "filmotekaDb")
public class NetworkDb {
    private int id;
    private String networkName;
    private String networkLogo;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "network_name", nullable = true, length = 64)
    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }

    @Basic
    @Column(name = "network_logo", nullable = true, length = -1)
    public String getNetworkLogo() {
        return networkLogo;
    }

    public void setNetworkLogo(String networkLogo) {
        this.networkLogo = networkLogo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NetworkDb networkDb = (NetworkDb) o;

        if (id != networkDb.id) return false;
        if (networkName != null ? !networkName.equals(networkDb.networkName) : networkDb.networkName != null)
            return false;
        if (networkLogo != null ? !networkLogo.equals(networkDb.networkLogo) : networkDb.networkLogo != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (networkName != null ? networkName.hashCode() : 0);
        result = 31 * result + (networkLogo != null ? networkLogo.hashCode() : 0);
        return result;
    }
}

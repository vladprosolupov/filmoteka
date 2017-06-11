package dao;

import javax.persistence.*;

/**
 * Created by vladyslavprosolupov on 11.06.17.
 */
@Entity
@Table(name = "Link_to_network", schema = "dbo", catalog = "filmotekaDb")
public class LinkToNetworkDb {
    private int id;
    private String link;
    private NetworkDb networkByIdNetwork;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "link", nullable = true, length = -1)
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinkToNetworkDb that = (LinkToNetworkDb) o;

        if (id != that.id) return false;
        if (link != null ? !link.equals(that.link) : that.link != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (link != null ? link.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "id_network", referencedColumnName = "id", nullable = false)
    public NetworkDb getNetworkByIdNetwork() {
        return networkByIdNetwork;
    }

    public void setNetworkByIdNetwork(NetworkDb networkByIdNetwork) {
        this.networkByIdNetwork = networkByIdNetwork;
    }
}

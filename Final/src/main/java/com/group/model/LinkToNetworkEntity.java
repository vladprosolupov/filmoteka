package com.group.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Link_to_network", schema = "dbo", catalog = "filmotekaDb")
public class LinkToNetworkEntity {
    private int id;
    private String link;
    private Collection<FilmNetworkEntity> filmNetworksById;
    private NetworkEntity networkByIdNetwork;

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

        LinkToNetworkEntity that = (LinkToNetworkEntity) o;

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

    @OneToMany(mappedBy = "linkToNetworkByIdLinkToNetwork")
    public Collection<FilmNetworkEntity> getFilmNetworksById() {
        return filmNetworksById;
    }

    public void setFilmNetworksById(Collection<FilmNetworkEntity> filmNetworksById) {
        this.filmNetworksById = filmNetworksById;
    }

    @ManyToOne
    @JoinColumn(name = "id_network", referencedColumnName = "id", nullable = false)
    public NetworkEntity getNetworkByIdNetwork() {
        return networkByIdNetwork;
    }

    public void setNetworkByIdNetwork(NetworkEntity networkByIdNetwork) {
        this.networkByIdNetwork = networkByIdNetwork;
    }
}

package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Film_network", schema = "dbo", catalog = "filmotekaDb")
public class FilmNetworkEntity {
    private int id;
    private int idFilm;
    private int idLinkToNetwork;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "id_film")
    public int getIdFilm() {
        return idFilm;
    }

    public void setIdFilm(int idFilm) {
        this.idFilm = idFilm;
    }

    @Basic
    @Column(name = "id_link_to_network")
    public int getIdLinkToNetwork() {
        return idLinkToNetwork;
    }

    public void setIdLinkToNetwork(int idLinkToNetwork) {
        this.idLinkToNetwork = idLinkToNetwork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmNetworkEntity that = (FilmNetworkEntity) o;

        if (id != that.id) return false;
        if (idFilm != that.idFilm) return false;
        if (idLinkToNetwork != that.idLinkToNetwork) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + idFilm;
        result = 31 * result + idLinkToNetwork;
        return result;
    }
}

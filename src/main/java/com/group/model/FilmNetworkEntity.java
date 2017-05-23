package com.group.model;

import javax.persistence.*;

/**
 * Created by 1rost on 5/22/2017.
 */
@Entity
@Table(name = "Film_network", schema = "dbo", catalog = "filmotekaDb")
public class FilmNetworkEntity {
    private int id;
    private FilmEntity filmByIdFilm;
    private LinkToNetworkEntity linkToNetworkByIdLinkToNetwork;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FilmNetworkEntity that = (FilmNetworkEntity) o;

        if (id != that.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @ManyToOne
    @JoinColumn(name = "id_film", referencedColumnName = "id", nullable = false)
    public FilmEntity getFilmByIdFilm() {
        return filmByIdFilm;
    }

    public void setFilmByIdFilm(FilmEntity filmByIdFilm) {
        this.filmByIdFilm = filmByIdFilm;
    }

    @ManyToOne
    @JoinColumn(name = "id_link_to_network", referencedColumnName = "id", nullable = false)
    public LinkToNetworkEntity getLinkToNetworkByIdLinkToNetwork() {
        return linkToNetworkByIdLinkToNetwork;
    }

    public void setLinkToNetworkByIdLinkToNetwork(LinkToNetworkEntity linkToNetworkByIdLinkToNetwork) {
        this.linkToNetworkByIdLinkToNetwork = linkToNetworkByIdLinkToNetwork;
    }
}

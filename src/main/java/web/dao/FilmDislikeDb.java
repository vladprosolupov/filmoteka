package web.dao;

import web.embeddable.FilmDislike;

import javax.persistence.*;

@Entity
@Table(name = "Film_dislike", schema = "dbo", catalog = "inzS13009")
public class FilmDislikeDb {
    @EmbeddedId
    private FilmDislike filmDislike;

    public FilmDislike getFilmDislike() {
        return filmDislike;
    }

    public void setFilmDislike(FilmDislike filmDislike) {
        this.filmDislike = filmDislike;
    }

    @Override
    public String toString() {
        return "FilmDislikeDb{" +
                "filmDislike=" + filmDislike +
                '}';
    }
}

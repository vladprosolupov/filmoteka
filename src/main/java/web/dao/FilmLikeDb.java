package web.dao;

import web.embeddable.FilmLike;

import javax.persistence.*;

@Entity
@Table(name = "Film_like", schema = "dbo", catalog = "filmotekaDb")
public class FilmLikeDb {
    @EmbeddedId
    private FilmLike filmLike;

    public FilmLike getFilmLike() {
        return filmLike;
    }

    public void setFilmLike(FilmLike filmLike) {
        this.filmLike = filmLike;
    }

    @Override
    public String toString() {
        return "FilmLikeDb{" +
                "filmLike=" + filmLike +
                '}';
    }
}
